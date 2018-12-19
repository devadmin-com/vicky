package com.devadmin.vicky.test;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.Changelog;
import com.devadmin.vicky.controller.jira.model.JiraChangeLogItemModel;

import java.util.List;

public class TestChangelog implements Changelog {

  private List<ChangeLogItem> items;

  @Override
  public List<ChangeLogItem> getItems() {
    return items;
  }

  public void setItems(List<ChangeLogItem> items) {
    this.items = items;
  }
}
