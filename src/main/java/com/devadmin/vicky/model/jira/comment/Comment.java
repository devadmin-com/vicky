/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.model.jira.comment;

import com.devadmin.vicky.model.jira.AuthorModel;

import java.util.Date;
import java.util.List;

/**
 * Comment on a task.
 */
public interface Comment {

    /**
     * @return List of usernames who was referenced in comment
     */
    List<String> getReferences();

    /**
     * @return comment author model
     */
    AuthorModel getAuthor();

    /**
     * @return comment body
     */
    String getBody();

    /**
     * @ return comment creation date
     */
    Date getCreated();

    /**
     * @return comment last update date
     */
    Date getUpdated();
}
