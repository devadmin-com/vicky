package com.devadmin.vicky;

import com.devadmin.vicky.controller.jira.model.JiraChangeLogItemModel;

import java.util.List;

public interface Changelog {

  List<ChangeLogItem> getItems();
}
