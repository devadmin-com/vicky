package com.devadmin.vicky.listener;

import com.devadmin.jira.JiraClient;
import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModel;
import com.devadmin.vicky.event.TaskEvent;
import com.devadmin.vicky.util.BlockerTaskTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * When a task is assigned to a user, send them a private message. (Implements Story TL-105 issue assigned -> slack
 * private message)
 * <p>
 * don't send notification if creator = assignee editor = assignee commenter = assignee mention in comment = assignee
 */
@Component
public class PMOnAssignListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PMOnAssignListener.class);

  private static final String PRIORITYBLOCKER = "Blocker";

  @Autowired
  private JiraClient jiraClient;

  @Autowired
  public PMOnAssignListener(MessageService messageService) {
    super(messageService);
  }

  public void onApplicationEvent(TaskEvent event) {

    TaskEventModel model = event.getTaskEventModel();

    if (model.getChangeLog() != null) {

      for (ChangeLogItem changeLogItem : model.getChangeLog().getItems()) {
        if (changeLogItem.isAssign() && changeLogItem.getTo() != null) {

          String message = "This message was sent by supercool Vicky 2.0 from PMOnAssignListener";

          String assignedTo = changeLogItem.getTo();

          if (model.getTask().getPriority() != null && PRIORITYBLOCKER.equals(model.getTask().getPriority())) {

            String blockerMessage = "This message was sent by supercool Vicky 2.0 from Blocker";

            BlockerTaskTracker tracker = new BlockerTaskTracker(model, blockerMessage, jiraClient, messageService,
                assignedTo);
            tracker.startTracking();
          }

          try {
            messageService.sendPrivateMessage(assignedTo, message);
          } catch (MessageServiceException e) {
            LOGGER.error(e.getMessage());
          }
        }
      }
    }
  }
}