/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.eventgrid.v2020_04_01_preview;

import com.microsoft.azure.arm.collection.SupportsCreating;
import rx.Completable;
import rx.Observable;
import com.microsoft.azure.management.eventgrid.v2020_04_01_preview.implementation.EventChannelsInner;
import com.microsoft.azure.arm.model.HasInner;

/**
 * Type representing EventChannels.
 */
public interface EventChannels extends SupportsCreating<EventChannel.DefinitionStages.Blank>, HasInner<EventChannelsInner> {
    /**
     * Get an event channel.
     * Get properties of an event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<EventChannel> getAsync(String resourceGroupName, String partnerNamespaceName, String eventChannelName);

    /**
     * List event channels.
     * List all the event channels in a partner namespace.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<EventChannel> listByPartnerNamespaceAsync(final String resourceGroupName, final String partnerNamespaceName);

    /**
     * Delete an event channel.
     * Delete existing event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Completable deleteAsync(String resourceGroupName, String partnerNamespaceName, String eventChannelName);

}