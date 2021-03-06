// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Immutable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ListAccountSasResponse model.
 */
@Immutable
public final class ListAccountSasResponseInner {
    /*
     * List SAS credentials of storage account.
     */
    @JsonProperty(value = "accountSasToken", access = JsonProperty.Access.WRITE_ONLY)
    private String accountSasToken;

    /**
     * Get the accountSasToken property: List SAS credentials of storage
     * account.
     * 
     * @return the accountSasToken value.
     */
    public String accountSasToken() {
        return this.accountSasToken;
    }
}
