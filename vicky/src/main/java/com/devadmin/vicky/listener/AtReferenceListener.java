
package com.devadmin.vicky.listener;

import com.devadmin.vicky.Formatter;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.VickyException;
import com.devadmin.vicky.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * When a user is referenced in a comment send them a private message
 *
 * Implements story: TL-106 @reference in comment  -> slack private message
 */
@Component
public class AtReferenceListener extends TaskToMessageListener {

  public AtReferenceListener(MessageService messageService, Formatter formatter) {
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