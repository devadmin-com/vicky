package com.devadmin.vicky.test;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.controller.jira.model.JiraChangeLogItemModel;

/**
 * Test item inside changelog
 * @see ChangeLogItem
 */
public class TestJiraChangeLogItem extends JiraChangeLogItemModel {

  private String field;
  private String to;

  @Override
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  @Override
  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  @Override
  public boolean isAssign() {
    return "assignee".equals(field);
  }
}
