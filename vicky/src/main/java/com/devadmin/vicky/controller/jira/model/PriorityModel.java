package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** This is the object which contains the information task priority */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PriorityModel {

  @JsonProperty("self")
  private String self;

  @JsonProperty("iconUrl")
  private String iconUrl;

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private String id;
}
