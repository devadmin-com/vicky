package com.devadmin.vicky;

/**
 * Type of change happened on task
 */
public enum ChangeType {
    ASSIGN("assignee"), DEFAULT("");

    private String changeType;

    ChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeType() {
        return changeType;
    }
}
