package com.devadmin.jira.greenhopper;

import com.devadmin.jira.RestClient;

/**
 * A base class for GreenHopper resources.
 */
public abstract class GreenHopperResource {

    protected static final String RESOURCE_URI = "/rest/greenhopper/1.0/";

    protected RestClient restclient = null;
    protected int id = 0;

    /**
     * Creates a new GreenHopper resource.
     *
     * @param restclient REST client instance
     */
    public GreenHopperResource(RestClient restclient) {
        this.restclient = restclient;
    }

    /**
     * Internal JIRA ID.
     */
    public int getId() {
        return id;
    }
}

