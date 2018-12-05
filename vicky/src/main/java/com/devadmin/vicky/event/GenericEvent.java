package com.devadmin.vicky.event;

public class GenericEvent<T> {
  private T eventModel;

  public GenericEvent(T eventModel) {
    this.eventModel = eventModel;
  }

  public T getEventModel() {
    return eventModel;
  }
}
