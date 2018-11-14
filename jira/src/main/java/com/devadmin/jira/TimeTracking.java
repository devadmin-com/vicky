package com.devadmin.jira;

import java.util.Map;

import net.sf.json.JSONObject;

/** Represents issue time tracking data. */
public class TimeTracking {

  private String originalEstimate = null;
  private String remainingEstimate = null;
  private String timeSpent = null;
  private Integer originalEstimateSeconds = null;
  private Integer remainingEstimateSeconds = null;
  private Integer timeSpentSeconds = null;

  /**
   * Creates a time tracking structure from a JSON payload.
   *
   * @param json JSON payload
   */
  protected TimeTracking(JSONObject json) {
    Map<?, ?> map = json;

    originalEstimate = Field.getString(map.get("originalEstimate"));
    remainingEstimate = Field.getString(map.get("remainingEstimate"));
    timeSpent = Field.getString(map.get("timeSpent"));
    originalEstimateSeconds = Field.getInteger(map.get("originalEstimateSeconds"));
    remainingEstimateSeconds = Field.getInteger(map.get("remainingEstimateSeconds"));
    timeSpentSeconds = Field.getInteger(map.get("timeSpentSeconds"));
  }

  public TimeTracking() {}

  public TimeTracking(TimeTracking tt) {
    this.originalEstimate = tt.originalEstimate;
    this.remainingEstimate = tt.remainingEstimate;
    this.originalEstimateSeconds = tt.originalEstimateSeconds;
    this.remainingEstimateSeconds = tt.remainingEstimateSeconds;
    this.timeSpent = tt.timeSpent;
    this.timeSpentSeconds = tt.timeSpentSeconds;
  }

  protected JSONObject toJsonObject() {
    JSONObject object = new JSONObject();
    if (originalEstimate != null) object.put("originalEstimate", originalEstimate);

    if (remainingEstimate != null) object.put("remainingEstimate", remainingEstimate);

    if (originalEstimateSeconds >= 0)
      object.put("originalEstimateSeconds", originalEstimateSeconds);

    if (remainingEstimateSeconds >= 0)
      object.put("remainingEstimateSeconds", remainingEstimateSeconds);

    return object;
  }

  public String getOriginalEstimate() {
    return originalEstimate;
  }

  public String getRemainingEstimate() {
    return remainingEstimate;
  }

  public String getTimeSpent() {
    return timeSpent;
  }

  public int getOriginalEstimateSeconds() {
    return originalEstimateSeconds;
  }

  public int getRemainingEstimateSeconds() {
    return remainingEstimateSeconds;
  }

  public void setOriginalEstimate(String originalEstimate) {
    this.originalEstimate = originalEstimate;
  }

  public void setRemainingEstimate(String remainingEstimate) {
    this.remainingEstimate = remainingEstimate;
  }

  public void setOrignalEstimateSeconds(int originalEstimateSeconds) {
    this.originalEstimateSeconds = originalEstimateSeconds;
  }

  public void setRemainingEstimateSeconds(int remainingEstimateSeconds) {
    this.remainingEstimateSeconds = remainingEstimateSeconds;
  }

  public int getTimeSpentSeconds() {
    return timeSpentSeconds;
  }
}
