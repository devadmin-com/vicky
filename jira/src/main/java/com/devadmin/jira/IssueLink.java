package com.devadmin.jira;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/** Represents an issue link. */
public class IssueLink extends Resource {

  private LinkType type = null;
  private Issue inwardIssue = null;
  private Issue outwardIssue = null;

  /**
   * Creates a issue link from a JSON payload.
   *
   * @param restclient REST client instance
   * @param json JSON payload
   */
  protected IssueLink(RestClient restclient, JSONObject json) {
    super(restclient);

    if (json != null) deserialise(json);
  }

  private void deserialise(JSONObject json) {
    Map map = json;

    self = Field.getString(map.get("self"));
    id = Field.getString(map.get("id"));
    type = Field.getResource(LinkType.class, map.get("type"), restclient);
    outwardIssue = Field.getResource(Issue.class, map.get("outwardIssue"), restclient);
    inwardIssue = Field.getResource(Issue.class, map.get("inwardIssue"), restclient);
  }

  /**
   * Deletes this issue link record.
   *
   * @throws JiraException when the delete fails
   */
  public void delete() throws JiraException {

    try {
      restclient.delete(getBaseUri() + "issueLink/" + id);
    } catch (Exception ex) {
      throw new JiraException("Failed to delete issue link " + id, ex);
    }
  }

  /**
   * Retrieves the given issue link record.
   *
   * @param restclient REST client instance
   * @param id Internal JIRA ID of the issue link
   * @return a issue link instance
   * @throws JiraException when the retrieval fails
   */
  public static IssueLink get(RestClient restclient, String id) throws JiraException {

    JSON result = null;

    try {
      result = restclient.get(getBaseUri() + "issueLink/" + id);
    } catch (Exception ex) {
      throw new JiraException("Failed to retrieve issue link " + id, ex);
    }

    if (!(result instanceof JSONObject)) throw new JiraException("JSON payload is malformed");

    return new IssueLink(restclient, (JSONObject) result);
  }

  @Override
  public String toString() {
    return String.format("%s %s", getType().getInward(), getOutwardIssue());
  }

  public LinkType getType() {
    return type;
  }

  public Issue getOutwardIssue() {
    return outwardIssue;
  }

  public Issue getInwardIssue() {
    return inwardIssue;
  }
}
