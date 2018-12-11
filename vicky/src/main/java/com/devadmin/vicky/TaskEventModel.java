package com.devadmin.vicky;

/*
    Models an event on a task.

    (this is used to allow listeners to not care what the implementation of the Task looks like)


 */
public interface TaskEventModel {

    /**
     * @return true if the event has comment
     */
    boolean hasComment();

    /**
        @return the task that this event happened on
     */
    Task getTask();

    /**
        When the event happened (on the server, not when we received it).

        @return when the event happened
     */
    public java.util.Date getTimeStamp();

    /**
     * What kind of event happened?
     *
     * @return the type of the event
     */
    public String getType();

}
