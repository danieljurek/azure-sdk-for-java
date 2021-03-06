// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The WebApplicationFirewallCustomRule model.
 */
@Fluent
public final class WebApplicationFirewallCustomRule {
    /*
     * Gets name of the resource that is unique within a policy. This name can
     * be used to access the resource.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * Gets a unique read-only string that changes whenever the resource is
     * updated.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /*
     * Describes priority of the rule. Rules with a lower value will be
     * evaluated before rules with a higher value.
     */
    @JsonProperty(value = "priority", required = true)
    private int priority;

    /*
     * Describes type of rule.
     */
    @JsonProperty(value = "ruleType", required = true)
    private WebApplicationFirewallRuleType ruleType;

    /*
     * List of match conditions.
     */
    @JsonProperty(value = "matchConditions", required = true)
    private List<MatchCondition> matchConditions;

    /*
     * Type of Actions.
     */
    @JsonProperty(value = "action", required = true)
    private WebApplicationFirewallAction action;

    /**
     * Get the name property: Gets name of the resource that is unique within a
     * policy. This name can be used to access the resource.
     * 
     * @return the name value.
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name property: Gets name of the resource that is unique within a
     * policy. This name can be used to access the resource.
     * 
     * @param name the name value to set.
     * @return the WebApplicationFirewallCustomRule object itself.
     */
    public WebApplicationFirewallCustomRule withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the etag property: Gets a unique read-only string that changes
     * whenever the resource is updated.
     * 
     * @return the etag value.
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Get the priority property: Describes priority of the rule. Rules with a
     * lower value will be evaluated before rules with a higher value.
     * 
     * @return the priority value.
     */
    public int priority() {
        return this.priority;
    }

    /**
     * Set the priority property: Describes priority of the rule. Rules with a
     * lower value will be evaluated before rules with a higher value.
     * 
     * @param priority the priority value to set.
     * @return the WebApplicationFirewallCustomRule object itself.
     */
    public WebApplicationFirewallCustomRule withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Get the ruleType property: Describes type of rule.
     * 
     * @return the ruleType value.
     */
    public WebApplicationFirewallRuleType ruleType() {
        return this.ruleType;
    }

    /**
     * Set the ruleType property: Describes type of rule.
     * 
     * @param ruleType the ruleType value to set.
     * @return the WebApplicationFirewallCustomRule object itself.
     */
    public WebApplicationFirewallCustomRule withRuleType(WebApplicationFirewallRuleType ruleType) {
        this.ruleType = ruleType;
        return this;
    }

    /**
     * Get the matchConditions property: List of match conditions.
     * 
     * @return the matchConditions value.
     */
    public List<MatchCondition> matchConditions() {
        return this.matchConditions;
    }

    /**
     * Set the matchConditions property: List of match conditions.
     * 
     * @param matchConditions the matchConditions value to set.
     * @return the WebApplicationFirewallCustomRule object itself.
     */
    public WebApplicationFirewallCustomRule withMatchConditions(List<MatchCondition> matchConditions) {
        this.matchConditions = matchConditions;
        return this;
    }

    /**
     * Get the action property: Type of Actions.
     * 
     * @return the action value.
     */
    public WebApplicationFirewallAction action() {
        return this.action;
    }

    /**
     * Set the action property: Type of Actions.
     * 
     * @param action the action value to set.
     * @return the WebApplicationFirewallCustomRule object itself.
     */
    public WebApplicationFirewallCustomRule withAction(WebApplicationFirewallAction action) {
        this.action = action;
        return this;
    }
}
