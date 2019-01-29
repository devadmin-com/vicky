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
    return String.format("%s assigned to you: %s", event.getActor(), super.format(event));
  }

}
