package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.event.TaskEvent;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * When a user is referenced in a comment send them a private message
 *
 * <p>Implements story: TL-106 @reference in comment -> slack private message
 */
@Component
public class AtReferenceListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AtReferenceListener.class);

  public AtReferenceListener(MessageService messageService) {
    super(messageService);
  }

  @EventListener
  public void onApplicationEvent(TaskEvent event) {
    if (event.getTaskEventModel().getComment() != null
        && event.getTaskEventModel().getComment().getReferences().size() > 0
        && TaskEventModelType.AT_REFERENCE.equals(event.getTaskEventModel().getType())) {

      LOGGER.info(event.toString());

      String message = "This message was sent by supercool Vicky 2.0 from AtReferenceListener";

      List<String> atReferences = event.getTaskEventModel().getComment().getReferences();

      for (String atReference : atReferences) {
        try {
          messageService.sendPrivateMessage(message, atReference);
        } catch (MessageServiceException e) {
          LOGGER.error(e.getMessage());
        }
      }
    }
  }
}
