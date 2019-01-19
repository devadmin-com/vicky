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
    StringBuffer message = new StringBuffer(128);
    message.append(this.formatBase(event));
    message.append(this.getShortDescription(event.getTask()));
    message.append(this.getLastCommenter(event.getTask()));
    message.append(" ➠ ");
    message.append(this.getLastComment(event.getTask()));
    return message.toString();
  }

}
