/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

/**
 * ChangeLogItem inside changelog
 * TODO needs more descriptive comment
 */
public interface ChangeLogItem {

  /**
   * @return what kind of change happened (ex: priority, reporter, status, etc.)
   */
  String getField();

  /**
   * TODO unclear what does "person interaction" mean?
   * @return the username of the person interaction
   */
  String getTo();

  /**
   * @return true if the changelog is a task assignment (could be first assignment or reassignment)
   */
  boolean isAssign();
}
