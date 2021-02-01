$Language = "java"
$LanguageDisplayName = "Java"
$PackageRepository = "Maven"
$packagePattern = "*.pom"
$MetadataUri = "https://raw.githubusercontent.com/Azure/azure-sdk/master/_data/releases/latest/java-packages.csv"
$BlobStorageUrl = "https://azuresdkdocs.blob.core.windows.net/%24web?restype=container&comp=list&prefix=java%2F&delimiter=%2F"

function Get-java-PackageInfoFromRepo ($pkgPath, $serviceDirectory, $pkgName)
{
  $projectPath = Join-Path $pkgPath "pom.xml"

  if (Test-Path $projectPath)
  {
    $projectData = New-Object -TypeName XML
    $projectData.load($projectPath)
    $projectPkgName = $projectData.project.artifactId
    $pkgVersion = $projectData.project.version
    $pkgGroup = $projectData.project.groupId

    if ($projectPkgName -eq $pkgName)
    {
        return [PackageProps]::new($pkgName, $pkgVersion.ToString(), $pkgPath, $serviceDirectory, $pkgGroup)
    }
  }
  return $null
}

# Returns the maven (really sonatype) publish status of a package id and version.
function IsMavenPackageVersionPublished($pkgId, $pkgVersion, $groupId)
{
  try 
  {
    $uri = "https://oss.sonatype.org/content/repositories/releases/$groupId/$pkgId/$pkgVersion/$pkgId-$pkgVersion.pom"
    $pomContent = Invoke-RestMethod -MaximumRetryCount 3 -RetryIntervalSec 10 -Method "GET" -uri $uri

    if ($pomContent -ne $null -or $pomContent.Length -eq 0)
    {
      return $true
    }
    else 
    {
      return $false
    }
  }
  catch 
  {
    $statusCode = $_.Exception.Response.StatusCode.value__
    $statusDescription = $_.Exception.Response.StatusDescription

    # if this is 404ing, then this pkg has never been published before
    if ($statusCode -eq 404) {
      return $false
    }

    Write-Host "VersionCheck to maven for packageId $pkgId failed with statuscode $statusCode"
    Write-Host $statusDescription
    exit(1)
  }
}

# Parse out package publishing information given a maven POM file
function Get-java-PackageInfoFromPackageFile ($pkg, $workingDirectory)
{
  [xml]$contentXML = Get-Content $pkg

  $pkgId = $contentXML.project.artifactId
  $pkgVersion = $contentXML.project.version
  $groupId = if ($contentXML.project.groupId -eq $null) { $contentXML.project.parent.groupId } else { $contentXML.project.groupId }
  $releaseNotes = ""
  $readmeContent = ""

  # if it's a snapshot. return $null (as we don't want to create tags for this, but we also don't want to fail)
  if ($pkgVersion.Contains("SNAPSHOT")) {
    return $null
  }

  $changeLogLoc = @(Get-ChildItem -Path $pkg.DirectoryName -Recurse -Include "$($pkg.Basename)-changelog.md")[0]
  if ($changeLogLoc) {
    $releaseNotes = Get-ChangeLogEntryAsString -ChangeLogLocation $changeLogLoc -VersionString $pkgVersion
  }

  $readmeContentLoc = @(Get-ChildItem -Path $pkg.DirectoryName -Recurse -Include "$($pkg.Basename)-readme.md")[0]
  if ($readmeContentLoc) {
    $readmeContent = Get-Content -Raw $readmeContentLoc
  }

  return New-Object PSObject -Property @{
    PackageId      = $pkgId
    GroupId        = $groupId
    PackageVersion = $pkgVersion
    ReleaseTag     = "$($pkgId)_$($pkgVersion)"
    Deployable     = $forceCreate -or !(IsMavenPackageVersionPublished -pkgId $pkgId -pkgVersion $pkgVersion -groupId $groupId.Replace(".", "/"))
    ReleaseNotes   = $releaseNotes
    ReadmeContent  = $readmeContent
  }
}

# Stage and Upload Docs to blob Storage
function Publish-java-GithubIODocs ($DocLocation, $PublicArtifactLocation)
{
  $PublishedDocs = Get-ChildItem "$DocLocation" | Where-Object -FilterScript {$_.Name.EndsWith("-javadoc.jar")}
  foreach ($Item in $PublishedDocs)
  {
    $UnjarredDocumentationPath = ""
    try
    {
      $PkgName = $Item.BaseName
      # The jar's unpacking command doesn't allow specifying a target directory
      # and will unjar all of the files in whatever the current directory is.
      # Create a subdirectory to unjar into, set the location, unjar and then
      # set the location back to its original location.
      $UnjarredDocumentationPath = Join-Path -Path $DocLocation -ChildPath $PkgName
      New-Item -ItemType directory -Path "$UnjarredDocumentationPath"
      $CurrentLocation = Get-Location
      Set-Location $UnjarredDocumentationPath
      jar -xf "$($Item.FullName)"
      Set-Location $CurrentLocation

      # If javadocs are produced for a library with source, there will always be an
      # index.html. If this file doesn't exist in the UnjarredDocumentationPath then
      # this is a sourceless library which means there are no javadocs and nothing
      # should be uploaded to blob storage.
      $IndexHtml = Join-Path -Path $UnjarredDocumentationPath -ChildPath "index.html"
      if (!(Test-Path -path $IndexHtml))
      {
        Write-Host "$($PkgName) does not have an index.html file, skippping."
        continue
      }

      # Get the POM file for the artifact we're processing
      $PomFile = $Item.FullName.Substring(0,$Item.FullName.LastIndexOf(("-javadoc.jar"))) + ".pom"
      Write-Host "PomFile $($PomFile)"

      # Pull the version from the POM
      [xml]$PomXml = Get-Content $PomFile
      $Version = $PomXml.project.version
      $ArtifactId = $PomXml.project.artifactId

      Write-Host "Start Upload for $($PkgName)/$($Version)"
      Write-Host "DocDir $($UnjarredDocumentationPath)"
      Write-Host "PkgName $($ArtifactId)"
      Write-Host "DocVersion $($Version)"
      $releaseTag = RetrieveReleaseTag $PublicArtifactLocation 
      Upload-Blobs -DocDir $UnjarredDocumentationPath -PkgName $ArtifactId -DocVersion $Version -ReleaseTag $releaseTag

    }
    Finally
    {
      if (![string]::IsNullOrEmpty($UnjarredDocumentationPath))
      {
        if (Test-Path -Path $UnjarredDocumentationPath)
        {
          Write-Host "Cleaning up $UnjarredDocumentationPath"
          Remove-Item -Recurse -Force $UnjarredDocumentationPath
        }
      }
    }
  }
}

function Get-java-GithubIoDocIndex()
{
  # Update the main.js and docfx.json language content
  UpdateDocIndexFiles -appTitleLang "Java"
  # Fetch out all package metadata from csv file.
  $metadata = Get-CSVMetadata -MetadataUri $MetadataUri
  # Leave the track 2 packages if multiple packages fetched out.
  $clientPackages = $metadata | Where-Object { $_.GroupId -eq 'com.azure' } 
  $nonClientPackages = $metadata | Where-Object { $_.GroupId -ne 'com.azure' -and !$clientPackages.Package.Contains($_.Package) }
  $uniquePackages = $clientPackages + $nonClientPackages
  # Get the artifacts name from blob storage
  $artifacts =  Get-BlobStorage-Artifacts -blobStorageUrl $BlobStorageUrl -blobDirectoryRegex "^java/(.*)/$" -blobArtifactsReplacement '$1'
  # Build up the artifact to service name mapping for GithubIo toc.
  $tocContent = Get-TocMapping -metadata $uniquePackages -artifacts $artifacts
  # Generate yml/md toc files and build site.
  GenerateDocfxTocContent -tocContent $tocContent -lang "Java"
}

# a "package.json configures target packages for all the monikers in a Repository, it also has a slightly different
# schema than the moniker-specific json config that is seen in python and js
function Update-java-CIConfig($pkgs, $ciRepo, $locationInDocRepo, $monikerId=$null)
{
  $pkgJsonLoc = (Join-Path -Path $ciRepo -ChildPath $locationInDocRepo)
  
  if (-not (Test-Path $pkgJsonLoc)) {
    Write-Error "Unable to locate package json at location $pkgJsonLoc, exiting."
    exit(1)
  }

  $allJsonData = Get-Content $pkgJsonLoc | ConvertFrom-Json

  $visibleInCI = @{}

  for ($i=0; $i -lt $allJsonData[$monikerId].packages.Length; $i++) {
    $pkgDef = $allJsonData[$monikerId].packages[$i]
    $visibleInCI[$pkgDef.packageArtifactId] = $i
  }

  foreach ($releasingPkg in $pkgs) {
    if ($visibleInCI.ContainsKey($releasingPkg.PackageId)) {
      $packagesIndex = $visibleInCI[$releasingPkg.PackageId]
      $existingPackageDef = $allJsonData[$monikerId].packages[$packagesIndex]
      $existingPackageDef.packageVersion = $releasingPkg.PackageVersion
    }
    else {
      $newItem = New-Object PSObject -Property @{ 
        packageDownloadUrl = "https://repo1.maven.org/maven2"
        packageGroupId = $releasingPkg.GroupId
        packageArtifactId = $releasingPkg.PackageId
        packageVersion = $releasingPkg.PackageVersion
        inputPath = @()
        excludePath = @()
      }

      $allJsonData[$monikerId].packages += $newItem
    }
  }

  $jsonContent = $allJsonData | ConvertTo-Json -Depth 10 | % {$_ -replace "(?m)  (?<=^(?:  )*)", "    " }

  Set-Content -Path $pkgJsonLoc -Value $jsonContent
}

function Update-java-MonikerConfig($SupersedingPackages, $CiConfigLocation, $previewMonikerIndex=$null, $latestMonikerIndex=$null) {
  if (-not (Test-Path $CiConfigLocation)) {
    Write-Error "Unable to locate package json at location $CiConfigLocation, exiting."
    exit(1)
  }

  $allJson  = Get-Content $CiConfigLocation | ConvertFrom-Json
  $supersedingPackagesCache = @{}
  foreach ($package in $SupersedingPackages) { 
    $supersedingPackagesCache["$($package.GroupId):$($package.PackageId)"] = $package
  }

  $allJson[$previewMonikerIndex].packages = $allJson[$previewMonikerIndex].packages `
    | Where-Object {
      # {
      #   "packageDownloadUrl": "https://repo1.maven.org/maven2",
      #   "packageGroupId": "com.azure",
      #   "packageArtifactId": "azure-core",
      #   "packageVersion": "1.3.0-beta.1",
      #   "inputPath": [],
      #   "excludePath": [],
      #   "excludepackages": "com.azure.core.implementation:com.azure.core.util.polling.implementation:com.azure.core.implementation.annotation:com.azure.core.implementation.serializer.jsonwrapper.api:com.azure.core.implementation.exception:com.azure.core.implementation.http:com.azure.core.implementation.serializer.jsonwrapper.jacksonwrapper:com.azure.core.implementation.serializer.jsonwrapper:com.azure.core.implementation.logging:com.azure.core.implementation.serializer:com.azure.core.implementation.serializer.jsonwrapper.spi"
      # },

      $packageKey = "$($_.packageGroupId):$($_.packageArtifactId)"
      
      # Keep packages which don't match superseding packages
      if (-not $supersedingPackagesCache.ContainsKey($packageKey)) { 
        return $true
      }


      $supersedingVersion = [AzureEngSemanticVersion]::ParseVersionString(
        $supersedingPackagesCache[$packageKey].PackageVersion
      )
      $previewVersion = [AzureEngSemanticVersion]::ParseVersionString($_.packageVersion)
    
      # 1.0.0 > 1.0.0b2, return $false to exclude from the packages array
      Write-Host "$packageKey@$supersedingVersion > $previewVersion ??"
      if ($supersedingVersion -gt $previewVersion) { 
        Write-Host "SUPERSEDED"
        return $false 
      }

      Write-Host "NOT SUPERSEDED"
      return $true
    }

  $jsonContent = $allJson | ConvertTo-Json -Depth 10 | % {$_ -replace "(?m)  (?<=^(?:  )*)", "    " }

  Set-Content -Path $CiConfigLocation -Value $jsonContent
}

function Test-java-PackageSupersedesAllPublishedPackages($packageInfo, $ciConfigLocation=$null, $latestMonikerId=$null) {
  # Java published package versions are stored in the CI config so business 
  # logic does not have to reach out to 3rd party services

  $currentVersion = [AzureEngSemanticVersion]::ParseVersionString($packageInfo.PackageVersion)

  $allJson  = Get-Content $ciConfigLocation | ConvertFrom-Json
  
  $supersedingPublishedVersions = $allJson[$latestMonikerId].packages `
    | Where-Object { 
      # Ignore packages which do not match the name
      if ($_.packageGroupId -ne $packageInfo.GroupId -or $_.packageArtifactId -ne $packageInfo.PackageId) { 
        return $false 
      }

      Write-Host "Checking $($_.packageGroupId):$($_.packageArtifactId)"
      $publishedVersion = [AzureEngSemanticVersion]::ParseVersionString($_.packageVersion)      
      $superseded = $publishedVersion -gt $currentVersion

      if ($superseded) { 
        Write-Host "$($packageInfo.GroupId):$($packageInfo.PackageId)@$($packageInfo.PackageVersion) is superseded by version $($_.packageVersion)"
      }
      return $superseded
    }

    # If Count == 0 there are no superseding packages return $true, else $false
    return ($supersedingPublishedVersions | Measure-Object).Count -eq 0
}

# function is used to filter packages to submit to API view tool
function Find-java-Artifacts-For-Apireview($artifactDir, $pkgName = "")
{
  Write-Host "Checking for source jar in artifact path $($artifactDir)"
  # Find all source jar files in given artifact directory
  $files = Get-ChildItem "${artifactDir}" | Where-Object -FilterScript {$_.Name.EndsWith("sources.jar")}
  if (!$files)
  {
    Write-Host "$($artifactDir) does not have any package"
    return $null
  }
  elseif($files.Count -ne 1)
  {
    Write-Host "$($artifactDir) should contain only one (1) published source jar package"
    Write-Host "No of Packages $($files.Count)"
    return $null
  }
  
  $packages = @{
    $files[0].Name = $files[0].FullName
  }

  return $packages
}

function SetPackageVersion ($PackageName, $Version, $ServiceDirectory, $ReleaseDate, $BuildType, $GroupId)
{
  if($null -eq $ReleaseDate)
  {
    $ReleaseDate = Get-Date -Format "yyyy-MM-dd"
  }
  python "$EngDir/versioning/set_versions.py" --build-type $BuildType --new-version $Version --ai $PackageName --gi $GroupId
  python "$EngDir/versioning/update_versions.py" --update-type library --build-type $BuildType --sr
  & "$EngCommonScriptsDir/Update-ChangeLog.ps1" -Version $Version -ServiceDirectory $ServiceDirectory -PackageName $PackageName `
  -Unreleased $False -ReplaceLatestEntryTitle $True -ReleaseDate $ReleaseDate
}

function GetExistingPackageVersions ($PackageName, $GroupId=$null)
{
  try {
    $Uri = 'https://search.maven.org/solrsearch/select?q=g:"' + $GroupId + '"+AND+a:"' + $PackageName +'"&core=gav&rows=20&wt=json'
    $existingVersion = Invoke-RestMethod -Method GET -Uri $Uri
    $existingVersion = $existingVersion.response.docs.v
    [Array]::Reverse($existingVersion)
    return $existingVersion
  }
  catch {
    LogError "Failed to retrieve package versions. `n$_"
    return $null
  }
}