package com.devadmin.vicky.event;

import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.JiraService;
import com.devadmin.vicky.service.dto.jira.JiraEventDto;
import com.devadmin.vicky.util.DozerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class JiraEventListener implements ApplicationListener<JiraEvent> {

    private final JiraService jiraService;

    private final DozerMapper dozerMapper;

    @Autowired
    public JiraEventListener(JiraService jiraService, DozerMapper dozerMapper) {
        this.jiraService = jiraService;
        this.dozerMapper = dozerMapper;
    }

    @Override
    public void onApplicationEvent(JiraEvent jiraEvent) {
        JiraEventDto jiraEventDto = dozerMapper.map(jiraEvent.getJiraEventModel(), JiraEventDto.class);
        try {
            jiraService.eventProcessing(jiraEventDto);
        } catch (VickyException e) {
            e.printStackTrace();
        }
    }
}