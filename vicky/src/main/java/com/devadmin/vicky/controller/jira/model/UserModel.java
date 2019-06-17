package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This is the object which contains the information about user (issue creator) setting
 * ignoreUnknown to true, to ignore fields which coming from json but don't have them in class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserModel {

    @JsonProperty("self")
    private String self;

    @JsonProperty("name")
    private String name;

    @JsonProperty("key")
    private String key;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("avatarUrls")
    private AvatarUrlModel avatarUrl;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("timeZone")
    private String timeZone;

}
