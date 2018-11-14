package com.devadmin.jira;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.http.HttpRequest;

/** Basic HTTP authentication credentials. */
public class TokenCredentials implements ICredentials {

  private String username;
  private String password;
  private String token;
  private String cookieName = "JSESSIONID";

  /**
   * Creates new basic HTTP credentials.
   *
   * @param username
   * @param password
   */
  public TokenCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public TokenCredentials(String jsessionId) {
    token = jsessionId;
  }

  /**
   * Sets the Authorization header for the given request.
   *
   * @param req HTTP request to authenticate
   */
  public void authenticate(HttpRequest req) {
    if (token != null) {
      req.addHeader("Cookie", cookieName + "=" + token + ";");
    }
  }

  /**
   * Gets the logon name representing these credentials.
   *
   * @return logon name as a string
   */
  public String getLogonName() {
    return username;
  }

  public void initialize(RestClient client) throws JiraException {
    if (token == null) {
      try {
        JSONObject req = new JSONObject();
        req.put("username", username);
        req.put("password", password);
        JSON json = client.post(Resource.getAuthUri() + "session", req);
        if (json instanceof JSONObject) {
          JSONObject jso = (JSONObject) json;
          jso = (JSONObject) jso.get("session");
          cookieName = (String) jso.get("name");
          token = (String) jso.get("value");
        }
      } catch (Exception ex) {
        throw new JiraException("Failed to login", ex);
      }
    }
  }

  public void logout(RestClient client) throws JiraException {
    if (token != null) {
      try {
        client.delete(Resource.getAuthUri() + "session");
      } catch (Exception e) {
        throw new JiraException("Failed to logout", e);
      }
    }
  }

  public String getToken() {
    return token;
  }
}
