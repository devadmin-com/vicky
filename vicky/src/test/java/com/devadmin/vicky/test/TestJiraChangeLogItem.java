package com.devadmin.vicky.test;

import com.devadmin.vicky.AssignChangeLogItem;
import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.ChangeType;

/**
 * Test item inside changelog
 *
 * @see ChangeLogItem
 */
public class TestJiraChangeLogItem implements AssignChangeLogItem {

  private String field;
  private String to;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  @Override
  public String getAssignedTo() {
    return to;
  }

  @Override
  public ChangeType getChangeType() {
    return "assignee".equals(field) ? ChangeType.ASSIGN : ChangeType.DEFAULT;
  }
}
