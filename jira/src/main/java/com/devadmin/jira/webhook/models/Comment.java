package com.devadmin.jira.webhook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
  @JsonProperty("self")
  private String self;
  @JsonProperty("id")
  private String id;
  @JsonProperty("author")
  private Author author;
  @JsonProperty("body")
  private String body;
  @JsonProperty("updateAuthor")
  private Author updateAuthor;
  @JsonProperty("created")
  private Date created;
  @JsonProperty("updated")
  private Date updated;
  @JsonProperty("jsdPublic")
  private Boolean jsdPublic;

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Author getUpdateAuthor() {
    return updateAuthor;
  }

  public void setUpdateAuthor(Author updateAuthor) {
    this.updateAuthor = updateAuthor;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public Boolean getJsdPublic() {
    return jsdPublic;
  }

  public void setJsdPublic(Boolean jsdPublic) {
    this.jsdPublic = jsdPublic;
  }
}
