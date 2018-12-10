package com.devadmin.vicky.event;

/**
 * A class is the generic event type
 *
 * @param <T> The type of event
 */
public class GenericEvent<T> {
  private T eventModel;
  private boolean assignee = false;

  public GenericEvent(T eventModel) {
    this.eventModel = eventModel;
  }

  public GenericEvent() {

  }

  public T getEventModel() {
    return eventModel;
  }

  public boolean isAssignee() {
    return assignee;
  }

  public void setAssignee(boolean assignee) {
    this.assignee = assignee;
  }
}
