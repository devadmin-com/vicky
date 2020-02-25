/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.model.jira.changelog;

/**
 * AssignChangeLogItem is changeLogItem with assignment change
 */
public interface AssignChangeLogItem extends ChangeLogItem {

    /**
     * @return username of assignee
     */
    String getAssignedTo();
}
