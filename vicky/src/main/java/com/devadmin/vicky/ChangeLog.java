/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.List;

/**
 * Describes all changes done on task
 */
//TODO: javadoc
public interface ChangeLog {

  List<ChangeLogItem> getItems();
}
