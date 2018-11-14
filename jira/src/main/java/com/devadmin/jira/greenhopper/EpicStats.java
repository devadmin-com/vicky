package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * GreenHopper epic statistics.
 */
public class EpicStats {

    private Double notDoneEstimate = null;
    private Double doneEstimate = null;
    private int estimated = 0;
    private int notEstimated = 0;
    private int notDone = 0;
    private int done = 0;

    /**
     * Creates an estimate sum from a JSON payload.
     *
     * @param json JSON payload
     */
    protected EpicStats(JSONObject json) {
        Map map = json;

        notDoneEstimate = Field.getDouble(map.get("notDoneEstimate"));
        doneEstimate = Field.getDouble(map.get("doneEstimate"));
        estimated = Field.getInteger(map.get("estimated"));
        notEstimated = Field.getInteger(map.get("notEstimated"));
        notDone = Field.getInteger(map.get("notDone"));
        done = Field.getInteger(map.get("done"));
    }

    public Double getNotDoneEstimate() {
        return notDoneEstimate;
    }

    public Double getDoneEstimate() {
        return doneEstimate;
    }

    public int getEstimated() {
        return estimated;
    }

    public int getNotEstimated() {
        return notEstimated;
    }

    public int getNotDone() {
        return notDone;
    }

    public int getDone() {
        return done;
    }
}

