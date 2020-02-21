package com.devadmin.vicky.model.jira.changelog;

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
