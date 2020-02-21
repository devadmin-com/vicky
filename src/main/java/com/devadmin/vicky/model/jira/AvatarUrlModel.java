package com.devadmin.vicky.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This is the object which contains the information related to User avatar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AvatarUrlModel {

    @JsonProperty("48x48")
    private String avatarUrl48x48;

    @JsonProperty("32x32")
    private String avatarUrl32x32;

    @JsonProperty("24x24")
    private String avatarUrl24x24;

    @JsonProperty("16x16")
    private String avatarUrl16x16;

}
