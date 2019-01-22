/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

/**
 * ChangeLogItem inside changelog
 * describes changes done on event (ex: priority change, status change etc.)
 */
public interface ChangeLogItem {

  /**
   * @return what kind of change happened (ex: priority, reporter, status, etc.)
   */
  ChangeType getChangeType();
}
