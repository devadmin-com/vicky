package com.devadmin.jira;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * Represents an issue priority.
 */
public class Priority extends Resource {

    private String iconUrl = null;
    private String name = null;

    /**
     * Creates a priority from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Priority(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        iconUrl = Field.getString(map.get("iconUrl"));
        name = Field.getString(map.get("name"));
    }

    /**
     * Retrieves the given priority record.
     *
     * @param restclient REST client instance
     * @param id Internal JIRA ID of the priority
     *
     * @return a priority instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Priority get(RestClient restclient, String id)
        throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(getBaseUri() + "priority/" + id);
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve priority " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Priority(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}

