package com.devadmin.vicky;

import java.util.List;

/**
 *  comment on a task
 */
public interface Comment {

    /**
     * @return List of people referenced in this comment
     */
    List<String> getReferences();
}
