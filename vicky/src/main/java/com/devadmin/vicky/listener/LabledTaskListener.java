
package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.Formatter;
import com.devadmin.vicky.VickyException;
import com.devadmin.vicky.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * On issue create or resolve, for issues with labels send issue updates to slack channel of that name.
 * TL-127 issue with label create, resolve -> #label slack channel
 *
 * If a issue has a label - e.g. server-order - and there is a channel with that name that vicky is part-of (public or private) then issue updates should be sent to this channel.
 * send as:
 * <issue type icon> <Number> (clickable URL) <Status>: <Summary> @<assignee nickname>
 * <commenter name> ➠ <latest comment>
 */
@Component
public class LabledTaskListener extends TaskToMessageListener {

  public LabledTaskListener(MessageService messageService, Formatter formatter) {
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