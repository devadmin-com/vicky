package com.devadmin.vicky;

/**
 * Type of change happened on task
 */
public enum ChangeType {
    //TODO: Javadoc - what does each one mean?
    ASSIGN("assignee"), DEFAULT("");

    private String changeType;

    ChangeType(String changeType) {
        this.changeType = changeType;
    }

    //TODO: Javadoc
    public String getChangeType() {
        return changeType;
    }
}
