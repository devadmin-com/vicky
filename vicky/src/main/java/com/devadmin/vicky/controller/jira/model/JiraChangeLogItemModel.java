package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.AssignChangeLogItem;
import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.ChangeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** @see ChangeLogItem */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JiraChangeLogItemModel implements AssignChangeLogItem {

  @JsonProperty("field")
  private String field;

  @JsonProperty("fieldtype")
  private String fieldType;

  @JsonProperty("fieldId")
  private String fieldId;

  @JsonProperty("from")
  private String from;

  @JsonProperty("fromString")
  private String fromString;

  @JsonProperty("to")
  private String to;

  @JsonProperty("toString")
  private String toString;

  /** @return username of assignee */
  @Override
  public String getAssignedTo() {
    return "assignee".equals(field) ? this.to : "";
  }

  /** @return what kind of change happened (ex: priority, reporter, status, etc.) */
  @Override
  public ChangeType getChangeType() {
    return "assignee".equals(field) ? ChangeType.ASSIGN : ChangeType.DEFAULT;
  }
}
