package com.devadmin.jira.greenhopper;

import com.devadmin.jira.Field;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** GreenHopper backlog data. */
public class Backlog {

  private RestClient restclient = null;
  private List<SprintIssue> issues = null;
  private List<SprintIssue> backlogIssues = null;
  private int rankCustomFieldId = 0;
  private List<Sprint> sprints = null;
  private List<RapidViewProject> projects = null;
  private List<Marker> markers = null;
  private List<Epic> epics = null;
  private boolean canEditEpics = false;
  private boolean canManageSprints = false;
  private boolean maxIssuesExceeded = false;
  private int queryResultLimit = 0;
  private Map<String, List<RapidViewVersion>> versionsPerProject = null;

  /**
   * Creates the backlog from a JSON payload.
   *
   * @param restclient REST client instance
   * @param json JSON payload
   */
  protected Backlog(RestClient restclient, JSONObject json) {
    this.restclient = restclient;

    if (json != null) deserialise(json);
  }

  private void deserialise(JSONObject json) {
    Map map = json;

    issues = GreenHopperField.getResourceArray(SprintIssue.class, map.get("issues"), restclient);
    rankCustomFieldId = Field.getInteger(map.get("rankCustomFieldId"));
    sprints = GreenHopperField.getResourceArray(Sprint.class, map.get("sprints"), restclient);
    projects =
        GreenHopperField.getResourceArray(RapidViewProject.class, map.get("projects"), restclient);
    markers = GreenHopperField.getResourceArray(Marker.class, map.get("markers"), restclient);
    canManageSprints = Field.getBoolean(map.get("canManageSprints"));
    maxIssuesExceeded = Field.getBoolean(map.get("maxIssuesExceeded"));
    queryResultLimit = Field.getInteger(map.get("queryResultLimit"));

    if (map.containsKey("epicData") && map.get("epicData") instanceof JSONObject) {
      Map epicData = (Map) map.get("epicData");

      epics = GreenHopperField.getResourceArray(Epic.class, epicData.get("epics"), restclient);
      canEditEpics = Field.getBoolean(epicData.get("canEditEpics"));
    }

    if (map.containsKey("versionData") && map.get("versionData") instanceof JSONObject) {
      Map verData = (JSONObject) map.get("versionData");

      if (verData.containsKey("versionsPerProject")
          && verData.get("versionsPerProject") instanceof JSONObject) {

        Map verMap = (Map) verData.get("versionsPerProject");
        versionsPerProject = new HashMap<String, List<RapidViewVersion>>();

        for (Map.Entry<String, Object> kvp :
            (Iterable<Map.Entry<String, Object>>) verMap.entrySet()) {

          if (!(kvp.getValue() instanceof JSONArray)) continue;

          List<RapidViewVersion> versions = new ArrayList<RapidViewVersion>();

          for (Object item : (JSONArray) kvp.getValue()) {
            if (!(item instanceof JSONObject)) continue;

            RapidViewVersion ver = new RapidViewVersion(restclient, (JSONObject) item);
            versions.add(ver);
          }

          versionsPerProject.put(kvp.getKey(), versions);
        }
      }
    }

    // determining which issues are actually in the backlog vs the sprints
    // fill in the issues into the single sprints and the backlog issue list respectively
    for (SprintIssue issue : issues) {
      boolean addedToSprint = false;
      for (Sprint sprint : sprints) {
        if (sprint.getIssuesIds().contains(issue.getId())) {
          sprint.getIssues().add(issue);
          addedToSprint = true;
        }
      }
      if (!addedToSprint) {
        if (backlogIssues == null) {
          backlogIssues = new ArrayList<SprintIssue>();
        }
        backlogIssues.add(issue);
      }
    }
  }

  /**
   * Retrieves the backlog data for the given rapid view.
   *
   * @param restclient REST client instance
   * @param rv Rapid View instance
   * @return the backlog
   * @throws JiraException when the retrieval fails
   */
  public static Backlog get(RestClient restclient, RapidView rv) throws JiraException {

    final int rvId = rv.getId();
    JSON result = null;

    try {
      URI reporturi =
          restclient.buildURI(
              GreenHopperResource.RESOURCE_URI + "xboard/plan/backlog/data",
              new HashMap<String, String>() {
                {
                  put("rapidViewId", Integer.toString(rvId));
                }
              });
      result = restclient.get(reporturi);
    } catch (Exception ex) {
      throw new JiraException("Failed to retrieve backlog data", ex);
    }

    if (!(result instanceof JSONObject)) throw new JiraException("JSON payload is malformed");

    return new Backlog(restclient, (JSONObject) result);
  }

  public List<SprintIssue> getIssues() {
    return issues;
  }

  public List<SprintIssue> getBacklogIssues() {
    return backlogIssues;
  }

  public int getRankCustomFieldId() {
    return rankCustomFieldId;
  }

  public List<Sprint> getSprints() {
    return sprints;
  }

  public List<RapidViewProject> getProjects() {
    return projects;
  }

  public List<Marker> getMarkers() {
    return markers;
  }

  public List<Epic> getEpics() {
    return epics;
  }

  public boolean canEditEpics() {
    return canEditEpics;
  }

  public boolean canManageSprints() {
    return canManageSprints;
  }

  public boolean maxIssuesExceeded() {
    return maxIssuesExceeded;
  }

  public int queryResultLimit() {
    return queryResultLimit;
  }

  public Map<String, List<RapidViewVersion>> getVersionsPerProject() {
    return versionsPerProject;
  }
}
