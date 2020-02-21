package com.devadmin.vicky.service;

import com.devadmin.vicky.model.jira.task.TaskEvent;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    public String getEventAutorName(TaskEvent event) {
        return event.getComment().getAuthor().getDisplayName();
    }
}

