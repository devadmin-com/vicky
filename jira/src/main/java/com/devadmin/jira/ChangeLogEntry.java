package com.devadmin.jira;

import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Contains information about an issue change log entry. Change log entries are
 * not returned by default when fetching issues. The <code>changelog</code>
 * field (expansion) must be explicitly provided in
 * {@link JiraClient#getIssue(String, String)}.
 */
public class ChangeLogEntry extends Resource {
    /**
     * Changelog author.
     */
    private User author = null;

    /**
     * Date when the changelog entry was created.
     */
    private Date created = null;

    /**
     * List of change log items in the change log entry.
     */
    private List<ChangeLogItem> items = null;

    /**
     * Creates a change log from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    protected ChangeLogEntry(RestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    /**
     * Deserializes a change log entry from a json payload.
     * @param json the json payload
     */
    private void deserialise(JSONObject json) {
        Map map = json;

        id = Field.getString(map.get("id"));
        author = Field.getResource(User.class, map.get("author"), restclient);
        created = Field.getDateTime(map.get("created"));
        items = Field.getResourceArray(ChangeLogItem.class, map.get(
                Field.CHANGE_LOG_ITEMS), restclient);
    }

    /**
     * Obtains the author of the change log entry.
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Returns the date when the change log entry was created.
     * @return the date
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Returns the list of items in the change log entry.
     * @return the list of items
     */
    public List<ChangeLogItem> getItems() {
        return items;
    }
}
