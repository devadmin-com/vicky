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
@Component("AssignFormatter")
public class AssignTaskEventFormatter extends SimpleTaskEventFormatter {

  public String format(TaskEvent event) {
    // TODO use string format

    StringBuffer message = new StringBuffer(128);
    message.append(event.getActor());
    message.append(" assigned to you: ");
    message.append(super.format(event));
    return message.toString();
  }

}
