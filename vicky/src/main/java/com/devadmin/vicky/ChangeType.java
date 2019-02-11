package com.devadmin.vicky;

/**
 * Type of change happened on task
 * ASSIGN is type of task change which happened because of assignment
 */
public enum ChangeType {

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
