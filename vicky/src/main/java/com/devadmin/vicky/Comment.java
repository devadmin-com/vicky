/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import com.devadmin.vicky.controller.jira.model.AuthorModel;

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
}
