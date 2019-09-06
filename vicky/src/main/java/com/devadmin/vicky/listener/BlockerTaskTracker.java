package com.devadmin.vicky.listener;

import com.devadmin.vicky.MessageService;
import com.devadmin.vicky.MessageServiceException;
import com.devadmin.vicky.Task;
import com.devadmin.vicky.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rcarz.jiraclient.Comment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * This class periodically checks if a BLOCKER task was commented within the last 6 hours.
 * <p>
 * <p>One instance of this class is needed per Blocker task
 */
@Component
@Slf4j
@AllArgsConstructor
public class BlockerTaskTracker {

    private static final long ONE_DAY = 1000L * 60L * 60L * 24L; // 24h
    private static final long SIX_HOURS = 1000L * 60L * 60L * 6L; // 6h
    private static final long ONE_HOUR = 1000L * 60L * 60L; // 1h
    private static final String COMMENT_MESSAGE =
            "This ticket has not been commented for more than 6 hours";
    private static final String CREATION_MESSAGE =
            "This ticket has not been commented for more than 24 hours";

    private TaskService jiraTaskService;

    private MessageService messageService;

    /**
     * gets all blocker tasks from jira, checks if each of them contains comment, sends PM to slack if
     * task was not commented for 24 hours, or last comment was done more than 6 hours ago
     */
    @Scheduled(fixedDelay = ONE_HOUR)
    public void handleBlockerTasks() {

        log.info("handleBlockerTasks() method");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDateTime today = LocalDateTime.now();
        List<Task> tasks = jiraTaskService.getBlockerTasks();

        for (Task task : tasks) {
            Comment lastComment = jiraTaskService.getLastCommentByTaskId(task.getId());
            String taskNameWithLink = "<" + task.getUrl() + "|" + task.getKey() + ">";
            if (lastComment != null) {
                log.info("Last comment of {} is null", task.getDescription());
                Date commentCreatedDate = lastComment.getCreatedDate();
                Instant instant = commentCreatedDate.toInstant();

                LocalDateTime lastCommentDateTime = instant.atZone(defaultZoneId).toLocalDateTime();

                long durationBetweenNowAndLastCommentCreation =
                        Duration.between(lastCommentDateTime, today).toMillis();
                if (durationBetweenNowAndLastCommentCreation >= SIX_HOURS) {
                    sendPrivateMessage(task.getAssignee(), taskNameWithLink + " " + COMMENT_MESSAGE);
                }

            } else {
                String taskCreatedDate = task.getFields().getCreatedDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");

                LocalDateTime creationDateTime = LocalDateTime.parse(taskCreatedDate, formatter);

                long durationBetweenNowAndTaskCreation =
                        Duration.between(creationDateTime, today).toMillis();
                if (durationBetweenNowAndTaskCreation >= ONE_DAY) {
                    sendPrivateMessage(task.getAssignee(), taskNameWithLink + CREATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Send a direct private message to a given assignee
     *
     * @param assignee the assignee to send the message to
     * @param message  the message to send
     */
    private void sendPrivateMessage(String assignee, String message) {
        try {
            messageService.sendPrivateMessage(assignee, message);
        } catch (MessageServiceException e) {
            log.error("was not able to send private message about Blocker task", e.getMessage());
        }
    }
}