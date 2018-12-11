package com.devadmin.vicky;

/*
    A Generic task in some task tracking system...

    Abstracts away the task tracking system so we can view jira, asana, etc. the same way in our code

 */
public interface Task {

    /**
     * @return this task's description
     */
    String getDescription();

    /**
     *
     * @return the priority of this task (as a string)
     */
    String getPriority();

    /**
     *
     * @return a string describing the type of this task.
     */
    String getType();
}
