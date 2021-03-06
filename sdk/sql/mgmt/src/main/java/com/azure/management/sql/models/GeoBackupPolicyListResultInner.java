// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.sql.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The GeoBackupPolicyListResult model. */
@Fluent
public final class GeoBackupPolicyListResultInner {
    /*
     * The list of geo backup policies.
     */
    @JsonProperty(value = "value")
    private List<GeoBackupPolicyInner> value;

    /**
     * Get the value property: The list of geo backup policies.
     *
     * @return the value value.
     */
    public List<GeoBackupPolicyInner> value() {
        return this.value;
    }

    /**
     * Set the value property: The list of geo backup policies.
     *
     * @param value the value value to set.
     * @return the GeoBackupPolicyListResultInner object itself.
     */
    public GeoBackupPolicyListResultInner withValue(List<GeoBackupPolicyInner> value) {
        this.value = value;
        return this;
    }
}
