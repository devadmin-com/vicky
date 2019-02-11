package com.devadmin.vicky;

/**
 * This enum contains all possible types of taskEventModel
 * CREATED is type of event which we set to eventModel when we receive task creation event
 * UPDATED is type of event which we set to eventModel when we receive task update event
 * COMMENT is type of event which we set to eventModel when we receive task create, update comment  event
 * DEFAULT is type of event which we set to eventModel when we receive different event which not described above
 */
public enum TaskEventType {
  CREATED, UPDATED, COMMENT, DEFAULT
}
