package com.devadmin.jira;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.Map;

/** Represents issue votes. */
public class Votes extends Resource {

  private int votes = 0;
  private boolean hasVoted = false;

  /**
   * Creates votes from a JSON payload.
   *
   * @param restclient REST client instance
   * @param json JSON payload
   */
  protected Votes(RestClient restclient, JSONObject json) {
    super(restclient);

    if (json != null) deserialise(json);
  }

  private void deserialise(JSONObject json) {
    Map map = json;

    self = Field.getString(map.get("self"));
    id = Field.getString(map.get("id"));
    votes = Field.getInteger(map.get("votes"));
    hasVoted = Field.getBoolean(map.get("hasVoted"));
  }

  /**
   * Retrieves the given votes record.
   *
   * @param restclient REST client instance
   * @param issue Internal JIRA ID of the issue
   * @return a votes instance
   * @throws JiraException when the retrieval fails
   */
  public static Votes get(RestClient restclient, String issue) throws JiraException {

    JSON result = null;

    try {
      result = restclient.get(getBaseUri() + "issue/" + issue + "/votes");
    } catch (Exception ex) {
      throw new JiraException("Failed to retrieve votes for issue " + issue, ex);
    }

    if (!(result instanceof JSONObject)) throw new JiraException("JSON payload is malformed");

    return new Votes(restclient, (JSONObject) result);
  }

  @Override
  public String toString() {
    return Integer.toString(getVotes());
  }

  public int getVotes() {
    return votes;
  }

  public boolean hasVoted() {
    return hasVoted;
  }
}
