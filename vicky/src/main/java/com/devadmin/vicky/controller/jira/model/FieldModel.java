package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This is the object which contains the information about fields which are describing jira issue
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FieldModel {

  @JsonProperty("issuetype")
  private IssueTypeModel issueType;

  @JsonProperty("timespent")
  private Long timespent;

  @JsonProperty("project")
  private ProjectModel project;

  @JsonProperty("summary")
  private String summary;

  @JsonProperty("assignee")
  private UserModel assignee;

  @JsonProperty("status")
  private StatusModel status;

  @JsonProperty("priority")
  private PriorityModel priority;

  @JsonProperty("created")
  private String createdDate;

  @JsonProperty("updated")
  private String updatedDate;

  @JsonProperty("labels")
  private String[] labels;

}
