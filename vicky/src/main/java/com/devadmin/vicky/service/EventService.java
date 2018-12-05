package com.devadmin.vicky.service;

import com.devadmin.vicky.exception.VickyException;
import com.devadmin.vicky.service.dto.EventDto;

public interface EventService {

  void eventProcessing(EventDto eventDto) throws VickyException;
}
