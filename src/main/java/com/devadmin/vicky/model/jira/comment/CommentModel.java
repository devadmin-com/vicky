package com.devadmin.vicky.model.jira.comment;

import com.devadmin.vicky.model.jira.AuthorModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the object contains information related to issue comment
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentModel implements Comment {

    @JsonProperty("self")
    private String self;

    @JsonProperty("id")
    private String id;

    @JsonProperty("author")
    private AuthorModel author;

    @JsonProperty("body")
    private String body;

    @JsonProperty("updateAuthor")
    private AuthorModel updateAuthor;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("updated")
    private Date updated;

    @JsonProperty("jsdPublic")
    private Boolean jsdPublic;

    /**
     * (non-javadoc)
     *
     * @see Comment#getReferences()
     */
    @Override
    public List<String> getReferences() {
        List<String> userNames = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[.*?\\]");
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            String uname = matcher.group().trim();
            userNames.add(uname.substring(2, uname.length() - 1));
        }
        return userNames;
    }
}
