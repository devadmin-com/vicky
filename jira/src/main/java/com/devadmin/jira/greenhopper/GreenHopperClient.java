package com.devadmin.jira.greenhopper;

import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import java.util.List;

/**
 * A GreenHopper extension to the JIRA client.
 */
public class GreenHopperClient {

    private RestClient restclient = null;

    /**
     * Creates a GreenHopper client.
     *
     * @param jira JIRA client
     */
    public GreenHopperClient(JiraClient jira) {
        restclient = jira.getRestClient();
    }

    /**
     * Retreives the rapid view with the given ID.
     *
     * @param id Rapid View ID
     *
     * @return a RapidView instance
     *
     * @throws JiraException when something goes wrong
     */
    public RapidView getRapidView(int id) throws JiraException {
        return RapidView.get(restclient, id);
    }

    /**
     * Retreives all rapid views visible to the session user.
     *
     * @return a list of rapid views
     *
     * @throws JiraException when something goes wrong
     */
    public List<RapidView> getRapidViews() throws JiraException {
        return RapidView.getAll(restclient);
    }
}

