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
   * @todo is this what we really want to do, or would be better off having a type enum like we do for TaskEventModel.getType?
   * @return true if the changelog is a task assignment (could be first assignment or reassignment)
   */
  boolean isAssign();
}
