package com.devadmin.vicky.event;

import com.devadmin.vicky.*;

/**
    An event happened on a {@link Task}...
 */
public class TaskEvent extends GenericEvent<com.devadmin.vicky.TaskEvent> {

    public TaskEvent(com.devadmin.vicky.TaskEvent model){
        super(model);
    }


    /**
     * @return the event model itself (same as getEventModel but returning concrete class)
     */
    public com.devadmin.vicky.TaskEvent getTaskEventModel() {
        return getEventModel();
    }

    /**
     *
     * @return the task for which this event happened
     */
    public Task getTask() {
        return getTaskEventModel().getTask();
    }
}
