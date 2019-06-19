package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This is the object which contains the information related task project
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectModel {

    @JsonProperty("self")
    private String self;

    @JsonProperty("id")
    private String id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;

    @JsonProperty("projectTypeKey")
    private String projectTypeKey;

    @JsonProperty("avatarUrls")
    private AvatarUrlModel avatarUrl;

}
