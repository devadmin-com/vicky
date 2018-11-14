package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import com.devadmin.jira.RestClient;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 * Represents a GreenHopper epic issue.
 */
public class Epic extends GreenHopperIssue {

    public String epicLabel = null;
    public String epicColour = null;
    public EpicStats epicStats = null;

    /**
     * Creates an epic issue from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected Epic(RestClient restclient, JSONObject json) {
        super(restclient, json);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        epicLabel = Field.getString(map.get("epicLabel"));
        epicColour = Field.getString(map.get("epicColor"));
        epicStats = GreenHopperField.getEpicStats(map.get("epicStats"));
    }

    public String getEpicLabel() {
        return epicLabel;
    }

    public String getEpicColour() {
        return epicColour;
    }

    public EpicStats getEpicStats() {
        return epicStats;
    }
}

