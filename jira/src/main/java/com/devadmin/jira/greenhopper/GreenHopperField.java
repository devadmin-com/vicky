package com.devadmin.jira.greenhopper;

import com.devadmin.jira.RestClient;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Utility functions for translating between JSON and fields.
 */
public final class GreenHopperField {

    public static final String DATE_TIME_FORMAT = "d/MMM/yy h:m a";

    public static final String NO_DATE = "None";

    private GreenHopperField() { }

    /**
     * Gets a date-time from the given object.
     *
     * @param dt Date-Time as a string
     *
     * @return the date-time or null
     */
    public static DateTime getDateTime(Object dt) {
        if(dt == null || ((String)dt).equals(NO_DATE)){
            return null;
        }
        return DateTime.parse((String)dt, DateTimeFormat.forPattern(DATE_TIME_FORMAT));
    }

    /**
     * Gets an epic stats object from the given object.
     *
     * @param es a JSONObject instance
     *
     * @return a EpicStats instance or null if es isn't a JSONObject instance
     */
    public static EpicStats getEpicStats(Object es) {
        EpicStats result = null;

        if (es instanceof JSONObject && !((JSONObject)es).isNullObject())
            result = new EpicStats((JSONObject)es);

        return result;
    }

    /**
     * Gets an estimate statistic object from the given object.
     *
     * @param es a JSONObject instance
     *
     * @return a EstimateStatistic instance or null if es isn't a JSONObject instance
     */
    public static EstimateStatistic getEstimateStatistic(Object es) {
        EstimateStatistic result = null;

        if (es instanceof JSONObject && !((JSONObject)es).isNullObject())
            result = new EstimateStatistic((JSONObject)es);

        return result;
    }

    /**
     * Gets an estimate sum object from the given object.
     *
     * @param es a JSONObject instance
     *
     * @return a EstimateSum instance or null if es isn't a JSONObject instance
     */
    public static EstimateSum getEstimateSum(Object es) {
        EstimateSum result = null;

        if (es instanceof JSONObject && !((JSONObject)es).isNullObject())
            result = new EstimateSum((JSONObject)es);

        return result;
    }

    /**
     * Gets a list of integers from the given object.
     *
     * @param ia a JSONArray instance
     *
     * @return a list of integers
     */
    public static List<Integer> getIntegerArray(Object ia) {
        List<Integer> results = new ArrayList<Integer>();

        if (ia instanceof JSONArray) {
            for (Object v : (JSONArray)ia)
                results.add((Integer)v);
        }

        return results;
    }

    /**
     * Gets a GreenHopper resource from the given object.
     *
     * @param type Resource data type
     * @param r a JSONObject instance
     * @param restclient REST client instance
     *
     * @return a Resource instance or null if r isn't a JSONObject instance
     */
    public static <T extends GreenHopperResource> T getResource(
        Class<T> type, Object r, RestClient restclient) {

        T result = null;

        if (r instanceof JSONObject && !((JSONObject)r).isNullObject()) {
            if (type == Epic.class)
                result = (T)new Epic(restclient, (JSONObject)r);
            else if (type == Marker.class)
                result = (T)new Marker(restclient, (JSONObject)r);
            else if (type == RapidView.class)
                result = (T)new RapidView(restclient, (JSONObject)r);
            else if (type == RapidViewProject.class)
                result = (T)new RapidViewProject(restclient, (JSONObject)r);
            else if (type == Sprint.class)
                result = (T)new Sprint(restclient, (JSONObject)r);
            else if (type == SprintIssue.class)
                result = (T)new SprintIssue(restclient, (JSONObject)r);
        }

        return result;
    }

    /**
     * Gets a list of GreenHopper resources from the given object.
     *
     * @param type Resource data type
     * @param ra a JSONArray instance
     * @param restclient REST client instance
     *
     * @return a list of Resources found in ra
     */
    public static <T extends GreenHopperResource> List<T> getResourceArray(
        Class<T> type, Object ra, RestClient restclient) {

        List<T> results = new ArrayList<T>();

        if (ra instanceof JSONArray) {
            for (Object v : (JSONArray)ra) {
                T item = getResource(type, v, restclient);
                if (item != null)
                    results.add(item);
            }
        }

        return results;
    }

    /**
     * Gets a list of strings from the given object.
     *
     * @param ia a JSONArray instance
     *
     * @return a list of strings
     */
    public static List<String> getStringArray(Object ia) {
        List<String> results = new ArrayList<String>();

        if (ia instanceof JSONArray) {
            for (Object v : (JSONArray)ia)
                results.add((String)v);
        }

        return results;
    }
}

