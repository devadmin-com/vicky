package com.devadmin.vicky.test;

import com.devadmin.vicky.model.jira.changelog.ChangeLog;
import com.devadmin.vicky.model.jira.changelog.ChangeLogItem;

import java.util.List;

public class TestChangeLog implements ChangeLog {

    private List<ChangeLogItem> items;

    @Override
    public List<ChangeLogItem> getItems() {
        return items;
    }

    public void setItems(List<ChangeLogItem> items) {
        this.items = items;
    }
}
