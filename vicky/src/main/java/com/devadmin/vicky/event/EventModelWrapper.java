package com.devadmin.vicky.event;

import org.springframework.context.ApplicationEvent;

/**
 * An event wrapper holding a model which describes the event.
 *
 * This allows listeners to not care about the implementation of the event model.
 *
 */
public abstract class EventModelWrapper<T> extends ApplicationEvent {
  private T eventModel;
  private boolean assignee = false;

  public EventModelWrapper(T eventModel) {
    super(eventModel);
    this.eventModel = eventModel;
  }

  public T getEventModel() {
    return eventModel;
  }

}
