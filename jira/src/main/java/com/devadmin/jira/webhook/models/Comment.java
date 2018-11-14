package com.devadmin.jira.webhook.models;

import java.util.Date;

public class Comment {
  private String self;
  private String id;
  private Author author;
  private String body;
  private Author updateAuthor;
  private Date created;
  private Date updated;
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
