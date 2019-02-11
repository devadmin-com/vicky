/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */
package com.devadmin.vicky.format;

import com.devadmin.vicky.TaskEvent;
import org.springframework.stereotype.Component;

/**
 * Implements formatting of assign @TaskEventModelWrapper for sending to a @MessageService
 *
 */
@Component("SummaryFormatter")
public class SummaryTaskEventFormatter extends SimpleTaskEventFormatter {

  public String format(TaskEvent event) {
    return String.format("%s %s %s ➠ %s",
        super.formatBase(event),
        super.getShortDescription(event),
        super.getLastCommenter(event),
        super.getLastComment(event));
  }

}
