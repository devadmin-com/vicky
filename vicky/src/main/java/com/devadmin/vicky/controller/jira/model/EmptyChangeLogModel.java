package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.ChangeLogItem;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is empty implementation of ChangeLogModel
 */
public class EmptyChangeLogModel extends ChangeLogModel {

  public List<ChangeLogItem> getItems() {
    return new ArrayList<>();
  }
}
