package com.devadmin.vicky;

/**
 * ChangeLogItem inside changelog
 */
public interface ChangeLogItem {

  String getField();

  /**
   * @return the username of the person interaction
   */
  String getTo();

  /**
   * @return true if the changelog is a task assignment (could be first assignment or reassignment)
   */
  boolean isAssign();
}
