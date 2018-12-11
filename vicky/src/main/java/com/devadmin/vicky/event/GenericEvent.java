package com.devadmin.vicky.event;

/**
 * An event wrapper holding a model which describes the event.
 *
 * This allows listeners to not care about the implementation of the event model.
 *
 */
public abstract class GenericEvent<T> {
  private T eventModel;
  private boolean assignee = false;

  public GenericEvent(T eventModel) {
    this.eventModel = eventModel;
  }

  public T getEventModel() {
    return eventModel;
  }

}
