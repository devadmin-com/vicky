package com.devadmin.vicky.format;

import com.devadmin.vicky.TaskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implements formatting of assign @TaskEventModelWrapper for sending to a @MessageService
 *
 */
@Component("SummaryFormatter")
public class SummaryTaskEventFormatter extends SimpleTaskEventFormatter {


  @Autowired
  public SummaryTaskEventFormatter() {

  }

  public String format(TaskEvent event) {
    StringBuffer message = new StringBuffer(128);
    message.append(super.formatBase(event));
    message.append(super.getShortDescription(event.getTask()));
    message.append(super.getLastCommenter(event.getTask().getId()));
    message.append(" ➠ ");
    message.append(super.getLastComment(event.getTask().getId()));
    return message.toString();
  }

}
