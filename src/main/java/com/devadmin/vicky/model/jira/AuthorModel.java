package com.devadmin.vicky.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.TimeZone;

/**
 * This is the object which contains the information related to Author (issue author, comment
 * author, etc.)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthorModel {

    @JsonProperty("self")
    private String self;

    @JsonProperty("name")
    private String name;

    @JsonProperty("key")
    private String key;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("avatarUrls")
    private AvatarUrlModel avatarUrlModel;

    //    https://id.atlassian.com/manage-profile/profile-and-visibility
    //    All users should have visibility level for email "Anyone"
    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("timeZone")
    private TimeZone timeZone;
}
