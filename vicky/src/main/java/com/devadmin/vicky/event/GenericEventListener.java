
package com.devadmin.vicky.event;

import com.devadmin.vicky.controller.model.EventModel;
import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.EventService;
import com.devadmin.vicky.service.dto.EventDto;
import com.devadmin.vicky.util.DozerMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GenericEventListener {

    private final EventService eventService;

    private final DozerMapper dozerMapper;

    public GenericEventListener(@Qualifier("jiraEventServiceImpl") EventService eventService, DozerMapper dozerMapper) {
        this.eventService = eventService;
        this.dozerMapper = dozerMapper;
    }

    @EventListener(GenericEvent.class)
    public void handleEvent(GenericEvent<EventModel> event) {
        EventDto eventDto = dozerMapper.map(event.getEventModel(), EventDto.class);
        try {
            eventService.eventProcessing(eventDto);
        } catch (VickyException e) {
            e.printStackTrace();
        }
    }
}