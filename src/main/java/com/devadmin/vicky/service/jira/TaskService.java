/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.service.jira;

import com.devadmin.vicky.model.jira.task.Task;
import net.rcarz.jiraclient.Comment;

import java.util.List;

/**
 * A generic task service.
 * <p>
 * <p>For now only used for getting list of blocker tasks
 */
public interface TaskService {
    /**
     * @return all open tasks in system which have TaskPriority.BLOCKER
     */
    List<Task> getBlockerTasks();

    /**
     * @param taskId
     * @return last comment on a task
     */
    Comment getLastCommentByTaskId(String taskId);
}
