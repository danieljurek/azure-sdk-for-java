// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.appservice;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * The ContainerInfo model.
 */
@Fluent
public final class ContainerInfo {
    /*
     * The currentTimeStamp property.
     */
    @JsonProperty(value = "currentTimeStamp")
    private OffsetDateTime currentTimeStamp;

    /*
     * The previousTimeStamp property.
     */
    @JsonProperty(value = "previousTimeStamp")
    private OffsetDateTime previousTimeStamp;

    /*
     * The currentCpuStats property.
     */
    @JsonProperty(value = "currentCpuStats")
    private ContainerCpuStatistics currentCpuStats;

    /*
     * The previousCpuStats property.
     */
    @JsonProperty(value = "previousCpuStats")
    private ContainerCpuStatistics previousCpuStats;

    /*
     * The memoryStats property.
     */
    @JsonProperty(value = "memoryStats")
    private ContainerMemoryStatistics memoryStats;

    /*
     * The name property.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * The id property.
     */
    @JsonProperty(value = "id")
    private String id;

    /*
     * The eth0 property.
     */
    @JsonProperty(value = "eth0")
    private ContainerNetworkInterfaceStatistics eth0;

    /**
     * Get the currentTimeStamp property: The currentTimeStamp property.
     * 
     * @return the currentTimeStamp value.
     */
    public OffsetDateTime currentTimeStamp() {
        return this.currentTimeStamp;
    }

    /**
     * Set the currentTimeStamp property: The currentTimeStamp property.
     * 
     * @param currentTimeStamp the currentTimeStamp value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withCurrentTimeStamp(OffsetDateTime currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
        return this;
    }

    /**
     * Get the previousTimeStamp property: The previousTimeStamp property.
     * 
     * @return the previousTimeStamp value.
     */
    public OffsetDateTime previousTimeStamp() {
        return this.previousTimeStamp;
    }

    /**
     * Set the previousTimeStamp property: The previousTimeStamp property.
     * 
     * @param previousTimeStamp the previousTimeStamp value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withPreviousTimeStamp(OffsetDateTime previousTimeStamp) {
        this.previousTimeStamp = previousTimeStamp;
        return this;
    }

    /**
     * Get the currentCpuStats property: The currentCpuStats property.
     * 
     * @return the currentCpuStats value.
     */
    public ContainerCpuStatistics currentCpuStats() {
        return this.currentCpuStats;
    }

    /**
     * Set the currentCpuStats property: The currentCpuStats property.
     * 
     * @param currentCpuStats the currentCpuStats value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withCurrentCpuStats(ContainerCpuStatistics currentCpuStats) {
        this.currentCpuStats = currentCpuStats;
        return this;
    }

    /**
     * Get the previousCpuStats property: The previousCpuStats property.
     * 
     * @return the previousCpuStats value.
     */
    public ContainerCpuStatistics previousCpuStats() {
        return this.previousCpuStats;
    }

    /**
     * Set the previousCpuStats property: The previousCpuStats property.
     * 
     * @param previousCpuStats the previousCpuStats value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withPreviousCpuStats(ContainerCpuStatistics previousCpuStats) {
        this.previousCpuStats = previousCpuStats;
        return this;
    }

    /**
     * Get the memoryStats property: The memoryStats property.
     * 
     * @return the memoryStats value.
     */
    public ContainerMemoryStatistics memoryStats() {
        return this.memoryStats;
    }

    /**
     * Set the memoryStats property: The memoryStats property.
     * 
     * @param memoryStats the memoryStats value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withMemoryStats(ContainerMemoryStatistics memoryStats) {
        this.memoryStats = memoryStats;
        return this;
    }

    /**
     * Get the name property: The name property.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: The name property.
     * 
     * @param name the name value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the id property: The id property.
     * 
     * @return the id value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set the id property: The id property.
     * 
     * @param id the id value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the eth0 property: The eth0 property.
     * 
     * @return the eth0 value.
     */
    public ContainerNetworkInterfaceStatistics eth0() {
        return this.eth0;
    }

    /**
     * Set the eth0 property: The eth0 property.
     * 
     * @param eth0 the eth0 value to set.
     * @return the ContainerInfo object itself.
     */
    public ContainerInfo withEth0(ContainerNetworkInterfaceStatistics eth0) {
        this.eth0 = eth0;
        return this;
    }
}
