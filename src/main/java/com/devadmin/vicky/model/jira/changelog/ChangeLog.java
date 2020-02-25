/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.model.jira.changelog;

import java.util.List;

/**
 * Describes all changes done on task This interface to enable concrete implementation to do
 * concrete system specific stuff ex: jira, asana etc.
 */
public interface ChangeLog {

    // gets the list of changes happened on task
    List<ChangeLogItem> getItems();
}
