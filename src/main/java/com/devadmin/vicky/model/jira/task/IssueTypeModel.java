package com.devadmin.vicky.model.jira.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This is the object which contains the information related to issue type
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueTypeModel {

    @JsonProperty("self")
    private String self;

    @JsonProperty("id")
    private String id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("iconUrl")
    private String iconUrl;

    @JsonProperty("name")
    private String name;

    @JsonProperty("subtask")
    private Boolean subTask;

    @JsonProperty("avatarId")
    private String avatarId;
}
