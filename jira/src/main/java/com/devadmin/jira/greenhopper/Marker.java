package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import com.devadmin.jira.RestClient;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper marker (a sprint that hasn't started).
 */
public class Marker extends GreenHopperResource {

    private String name = null;

    /**
     * Creates a marker from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Marker(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getInteger(map.get("id"));
        name = Field.getString(map.get("name"));
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}

