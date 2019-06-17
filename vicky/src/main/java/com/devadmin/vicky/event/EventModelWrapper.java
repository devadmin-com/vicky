package com.devadmin.vicky.event;

import org.springframework.context.ApplicationEvent;

/**
 * An event wrapper holding a model which describes the event.
 * <p>
 * <p>This allows listeners to not care about the implementation of the event model.
 */
abstract class EventModelWrapper<T> extends ApplicationEvent {
    private T eventModel;

    EventModelWrapper(T eventModel) {
        super(eventModel);
        this.eventModel = eventModel;
    }

    public T getEventModel() {
        return eventModel;
    }
}
