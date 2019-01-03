/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky;

import java.util.List;

/**
 *  Comment on a task.
 */
public interface Comment {

    /**
     * TODO what is the String returned? name? email? some other identifier?
     * @return List of people referenced in this comment
     */
    List<String> getReferences();

    /**
     * TODO this is unused?
     * @return true if there is at least one reference to user i.e. list returned by @getReferences is not-empty
     */
    boolean hasReferences();
}
