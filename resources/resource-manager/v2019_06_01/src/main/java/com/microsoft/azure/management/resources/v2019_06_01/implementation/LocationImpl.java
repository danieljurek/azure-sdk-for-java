/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.resources.v2019_06_01.implementation;

import com.microsoft.azure.management.resources.v2019_06_01.Location;
import com.microsoft.azure.arm.model.implementation.WrapperImpl;
import rx.Observable;

class LocationImpl extends WrapperImpl<LocationInner> implements Location {
    private final ResourcesManager manager;

    LocationImpl(LocationInner inner,  ResourcesManager manager) {
        super(inner);
        this.manager = manager;
    }

    @Override
    public ResourcesManager manager() {
        return this.manager;
    }



    @Override
    public String displayName() {
        return this.inner().displayName();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String latitude() {
        return this.inner().latitude();
    }

    @Override
    public String longitude() {
        return this.inner().longitude();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String subscriptionId() {
        return this.inner().subscriptionId();
    }

}
