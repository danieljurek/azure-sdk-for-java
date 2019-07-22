/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.v2019_06_01.implementation;

import com.microsoft.azure.arm.resources.models.implementation.GroupableResourceCoreImpl;
import com.microsoft.azure.management.network.v2019_06_01.ExpressRouteServiceProvider;
import rx.Observable;
import java.util.List;
import com.microsoft.azure.management.network.v2019_06_01.ExpressRouteServiceProviderBandwidthsOffered;

class ExpressRouteServiceProviderImpl extends GroupableResourceCoreImpl<ExpressRouteServiceProvider, ExpressRouteServiceProviderInner, ExpressRouteServiceProviderImpl, NetworkManager> implements ExpressRouteServiceProvider {
    ExpressRouteServiceProviderImpl(String name, ExpressRouteServiceProviderInner inner, NetworkManager manager) {
        super(name, inner, manager);
    }

    @Override
    public Observable<ExpressRouteServiceProvider> createResourceAsync() {
        ExpressRouteServiceProvidersInner client = this.manager().inner().expressRouteServiceProviders();
        return null; // NOP createResourceAsync implementation as create is not supported
    }

    @Override
    public Observable<ExpressRouteServiceProvider> updateResourceAsync() {
        ExpressRouteServiceProvidersInner client = this.manager().inner().expressRouteServiceProviders();
        return null; // NOP updateResourceAsync implementation as update is not supported
    }

    @Override
    protected Observable<ExpressRouteServiceProviderInner> getInnerAsync() {
        ExpressRouteServiceProvidersInner client = this.manager().inner().expressRouteServiceProviders();
        return null; // NOP getInnerAsync implementation as get is not supported
    }



    @Override
    public List<ExpressRouteServiceProviderBandwidthsOffered> bandwidthsOffered() {
        return this.inner().bandwidthsOffered();
    }

    @Override
    public List<String> peeringLocations() {
        return this.inner().peeringLocations();
    }

    @Override
    public String provisioningState() {
        return this.inner().provisioningState();
    }

}
