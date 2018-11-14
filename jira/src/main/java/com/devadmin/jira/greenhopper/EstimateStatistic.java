
package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * GreenHopper estimate statistics for rapid views.
 */
public class EstimateStatistic {

    private String statFieldId = null;
    private Double statFieldValue = 0.0;
    private String statFieldText = null;

    /**
     * Creates an estimate statistic from a JSON payload.
     *
     * @param json JSON payload
     */
    protected EstimateStatistic(JSONObject json) {
        Map map = json;

        statFieldId = Field.getString(map.get("statFieldId"));

        if (map.containsKey("statFieldValue") &&
            map.get("statFieldValue") instanceof JSONObject) {

            Map val = (Map)json.get("statFieldValue");

            statFieldValue = Field.getDouble(val.get("value"));
            statFieldText = Field.getString(val.get("text"));
        }
    }

    public String getFieldId() {
        return statFieldId;
    }

    public Double getFieldValue() {
        return statFieldValue;
    }

    public String getFieldText() {
        return statFieldText;
    }
}

