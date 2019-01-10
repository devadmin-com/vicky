/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.List;

// TODO make l capital (if we keep the interface)
public interface Changelog {

  List<ChangeLogItem> getItems();
}
