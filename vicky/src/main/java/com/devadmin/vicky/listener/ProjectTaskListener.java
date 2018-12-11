
package com.devadmin.vicky.listener;

import com.devadmin.vicky.Formatter;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.VickyException;
import com.devadmin.vicky.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve send update to project's channel
 *
 * Implements Story: TL-99 issue create, resolve -> project channel
 */
@Component
public class ProjectTaskListener extends TaskToMessageListener {

  public ProjectTaskListener(MessageService messageService, Formatter formatter) {
    super(messageService,formatter);
  }

  @EventListener(
      classes = TaskEvent.class/*,
      condition =
          "#jiraEventModel.eventModel.changeLog != null and " +
              "#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_created') or " +
              "(#jiraEventModel.eventModel.webhookEvent.equals('jira:issue_updated') and " +
              "#jiraEventModel.eventModel.issue.fields.status.name.equals('Resolved 解決済'))"*/
  )
  public void handle(TaskEvent event) throws VickyException {
    System.err.print("Got event!" + event);
  }

}