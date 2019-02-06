package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEvent;
import com.devadmin.vicky.TaskEventFormatter;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * If task commented and commenter and assignee is not the same user send assignee PM message
 */
@Component
public class CommentedTaskListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommentedTaskListener.class);

  @Autowired
  public CommentedTaskListener(MessageService messageService,
      @Qualifier("SimpleFormatter") TaskEventFormatter taskEventFormatter) {
    super(messageService, taskEventFormatter);
  }

  @Override
  public void onApplicationEvent(TaskEventModelWrapper eventWrapper) {
    TaskEvent event = eventWrapper.getTaskEventModel();

    if (event.getComment() != null && !event.getComment().getAuthor().getName().equals(event.getTask().getAssignee())) {
      try {
        messageService.sendPrivateMessage(event.getComment().getAuthor().getName(), formatter.format(event));
      } catch (MessageServiceException e) {
        LOGGER.error(e.getMessage());
      }
    }
  }
}
