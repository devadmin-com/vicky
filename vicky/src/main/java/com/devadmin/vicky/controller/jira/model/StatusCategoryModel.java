package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/** This is the object which contains the information related category of task status */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusCategoryModel {

  @JsonProperty("self")
  private String self;

  @JsonProperty("id")
  private String id;

  @JsonProperty("key")
  private String key;

  @JsonProperty("colorName")
  private String colorName;

  @JsonProperty("name")
  private String name;
}
