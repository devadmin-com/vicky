package com.devadmin.jira;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/** Issue change log. */
public class ChangeLog extends Resource {
  /** List of change log entries. */
  private List<ChangeLogEntry> entries = null;

  /**
   * Creates a change log from a JSON payload.
   *
   * @param restclient REST client instance
   * @param json JSON payload
   */
  protected ChangeLog(RestClient restclient, JSONObject json) {
    super(restclient);

    if (json != null) deserialise(json);
  }

  /**
   * Deserializes a change log from a json payload.
   *
   * @param json the json payload
   */
  private void deserialise(JSONObject json) {
    Map map = json;

    entries =
        Field.getResourceArray(ChangeLogEntry.class, map.get(Field.CHANGE_LOG_ENTRIES), restclient);
  }

  /**
   * Returns the list of change log entries in the change log.
   *
   * @return the list of entries
   */
  public List<ChangeLogEntry> getEntries() {
    return entries;
  }
}
