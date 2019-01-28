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
    //TODO use string format 13
    StringBuffer message = new StringBuffer(128);
    message.append(super.formatBase(event));
    message.append(super.getShortDescription(event.getTask()));
    message.append(super.getLastCommenter(event.getTask()));
    message.append(" ➠ ");
    message.append(super.getLastComment(event.getTask()));
    return message.toString();
  }

}
