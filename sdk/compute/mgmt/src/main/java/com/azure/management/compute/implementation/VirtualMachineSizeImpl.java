// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.management.compute.implementation;

import com.azure.management.compute.VirtualMachineSize;
import com.azure.management.compute.models.VirtualMachineSizeInner;
import com.azure.management.resources.fluentcore.utils.Utils;

/**
 * The implementation for {@link VirtualMachineSize}.
 */
class VirtualMachineSizeImpl implements VirtualMachineSize {
    private VirtualMachineSizeInner innerModel;

    VirtualMachineSizeImpl(VirtualMachineSizeInner innerModel) {
        this.innerModel = innerModel;
    }

    @Override
    public String name() {
        return innerModel.name();
    }

    @Override
    public int numberOfCores() {
        return Utils.toPrimitiveInt(innerModel.numberOfCores());
    }

    @Override
    public int osDiskSizeInMB() {
        return Utils.toPrimitiveInt(innerModel.osDiskSizeInMB());
    }

    @Override
    public int resourceDiskSizeInMB() {
        return Utils.toPrimitiveInt(innerModel.resourceDiskSizeInMB());
    }

    @Override
    public int memoryInMB() {
        return Utils.toPrimitiveInt(innerModel.memoryInMB());
    }

    @Override
    public int maxDataDiskCount() {
        return Utils.toPrimitiveInt(innerModel.maxDataDiskCount());
    }
}
