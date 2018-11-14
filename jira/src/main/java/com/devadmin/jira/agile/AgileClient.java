package com.devadmin.jira.agile;

import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.jira.RestClient;
import java.util.List;

/**
 * An Agile extension to the JIRA client.
 *
 * @author pldupont
 * @see "https://docs.atlassian.com/jira-software/REST/cloud/"
 */
public class AgileClient {

    private RestClient restclient = null;

    /**
     * Creates an Agile client.
     *
     * @param jira JIRA client
     */
    public AgileClient(JiraClient jira) {
        restclient = jira.getRestClient();
    }

    /**
     * Retrieves the board with the given ID.
     *
     * @param id Board ID
     * @return a Board instance
     * @throws JiraException when something goes wrong
     */
    public Board getBoard(long id) throws JiraException {
        return Board.get(restclient, id);
    }

    /**
     * Retrieves all boards visible to the session user.
     *
     * @return a list of boards
     * @throws JiraException when something goes wrong
     */
    public List<Board> getBoards() throws JiraException {
        return Board.getAll(restclient);
    }

    /**
     * Retrieves the sprint with the given ID.
     *
     * @param id Sprint ID
     * @return a Sprint instance
     * @throws JiraException when something goes wrong
     */
    public Sprint getSprint(long id) throws JiraException {
        return Sprint.get(restclient, id);
    }

    /**
     * Retrieves the issue with the given ID.
     *
     * @param id Issue ID
     * @return an Issue instance
     * @throws JiraException when something goes wrong
     */
    public Issue getIssue(long id) throws JiraException {
        return Issue.get(restclient, id);
    }

    /**
     * Retrieves the issue with the given Key.
     *
     * @param key Issue Key
     * @return an Issue instance
     * @throws JiraException when something goes wrong
     */
    public Issue getIssue(String key) throws JiraException {
        return Issue.get(restclient, key);
    }

    /**
     * Retrieves the epic with the given ID.
     *
     * @param id Epic ID
     * @return an Epic instance
     * @throws JiraException when something goes wrong
     */
    public Epic getEpic(long id) throws JiraException {
        return Epic.get(restclient, id);
    }

    public RestClient getRestclient() {
        return restclient;
    }
}

