package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the object contains information related to issue comment
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentModel  implements Comment {
  @JsonProperty("self")
  private String self;
  @JsonProperty("id")
  private String id;
  @JsonProperty("author")
  private AuthorModel author;
  @JsonProperty("body")
  private String body;
  @JsonProperty("updateAuthor")
  private AuthorModel updateAuthor;
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

  public AuthorModel getAuthor() {
    return author;
  }

  public void setAuthor(AuthorModel author) {
    this.author = author;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public AuthorModel getUpdateAuthor() {
    return updateAuthor;
  }

  public void setUpdateAuthor(AuthorModel updateAuthor) {
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

  @Override
  public List<String> getReferences() {
    // @ToDo please implement me :) find them from body ;)
    return new ArrayList<>();
  }
}
