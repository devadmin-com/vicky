package com.devadmin.vicky.format;

import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implements formatting of assign @TaskEventModelWrapper for sending to a @MessageService
 *
 */
@Component
public class AssignTaskEventFormatter extends SimpleTaskEventFormatter {


  @Autowired
  public AssignTaskEventFormatter() {

  }

  public String format(TaskEvent event) {

    StringBuffer message = new StringBuffer(128);
    message.append(event.getActor())'
    message.append(" assigned to you: ");
    message.append(super.format(event));
    return message.toString();
  }

}
