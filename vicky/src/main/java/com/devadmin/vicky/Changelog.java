/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.List;

// TODO do we need this class? It's just a list of ChangeLogItem, why not just use List<ChangeLogItem> instead?
public interface Changelog {

  List<ChangeLogItem> getItems();
}
