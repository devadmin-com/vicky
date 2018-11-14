package com.devadmin.jira;

import net.sf.json.JSONObject;
import java.util.Map;

/**
 * Represents an issue priority.
 */
public class Transition extends Resource {

    private String name = null;
    private Status toStatus = null;
    private Map fields = null;

    /**
     * Creates a priority from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Transition(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        name = Field.getString(map.get("name"));
        toStatus = Field.getResource(Status.class, map.get(Field.TRANSITION_TO_STATUS), restclient);

        fields = (Map)map.get("fields");
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public Status getToStatus() {
        return toStatus;
    }

    public Map getFields() {
        return fields;
    }

}

