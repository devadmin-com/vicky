package com.devadmin.vicky.test;

import com.devadmin.vicky.ChangeLog;
import com.devadmin.vicky.ChangeLogItem;

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
