package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.TaskEventModel;
import com.devadmin.vicky.event.TaskEvent;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * When a user is referenced in a comment send them a private message
 *
 * <p>Implements story: TL-106 @reference in comment -> slack private message</p>
 */
@Component
public class AtReferenceListener extends TaskToMessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AtReferenceListener.class);

  public AtReferenceListener(MessageService messageService) {
    super(messageService);
  }

  public void onApplicationEvent(TaskEvent event) {
    TaskEventModel model = event.getTaskEventModel();
    if (model.hasComment()) {

      String message = "This message was sent by supercool Vicky 2.0 from AtReferenceListener";

      List<String> atReferences = model.getComment().getReferences();

      for (String atReference : atReferences) {
        try {
          messageService.sendPrivateMessage(atReference, message);
        } catch (MessageServiceException e) {
          LOGGER.error(e.getMessage());
        }
      }
    }
  }
}
