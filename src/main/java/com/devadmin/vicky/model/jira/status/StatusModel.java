package com.devadmin.vicky.model.jira.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This is the object which contains the information related to task status
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusModel {

    public static final String RESOLVED = "Resolved 解決済";

    @JsonProperty("self")
    private String self;

    @JsonProperty("description")
    private String description;

    @JsonProperty("iconUrl")
    private String iconUrl;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    @JsonProperty("statusCategory")
    private StatusCategoryModel statusCategory;

}
