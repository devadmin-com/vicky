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
            " This ticket has not been commented for more than 6 hours";
    private static final String CREATION_MESSAGE =
            " WARNING!! This ticket has not been commented for more than 24 hours, anyone who see this message, please inform assignee";

    private TaskService jiraTaskService;

    private MessageService messageService;

    /**
     * gets all blocker tasks from jira, checks if each of them contains comment, sends PM to slack if
     * task was not commented for 24 hours, or last comment was done more than 6 hours ago
     */
    @Scheduled(fixedDelay = ONE_HOUR)
    public void handleBlockerTasks() {

        log.info("handleBlockerTasks() method");
        LocalDateTime currentTime = LocalDateTime.now();
        List<Task> tasks = jiraTaskService.getBlockerTasks();

        for (Task task : tasks) {
            Comment lastComment = jiraTaskService.getLastCommentByTaskId(task.getId());
            String taskNameWithLink = "<" + task.getUrl() + "|" + task.getKey() + ">";
            if (lastComment != null) {
                checkAndWarnIfNoCommentsForLongTime(task, lastComment, currentTime, taskNameWithLink);
            } else {
                checkAndWarnIfNoWorkForLongTime(task, currentTime, taskNameWithLink);
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

    private void checkAndWarnIfNoCommentsForLongTime(Task task, Comment lastComment, LocalDateTime currentTime, String taskNameWithLink) {
        long durationBetweenNowAndLastCommentCreation = secondsSinceLastComment(task, lastComment, currentTime);

        if (shouldSendNoCommentWarning(durationBetweenNowAndLastCommentCreation)) {
            sendPrivateMessage(task.getFields().getAssignee().getEmailAddress(), taskNameWithLink + " " + COMMENT_MESSAGE);
        }
    }

    private void checkAndWarnIfNoWorkForLongTime(Task task, LocalDateTime currentTime, String taskNameWithLink) {
        long secondsBetweenNowAndTaskCreation = secondsSinceIssueCreated(task, currentTime);

        if (shouldSendTicketStillOpenWarning(secondsBetweenNowAndTaskCreation)) {
            sendPrivateMessage(task.getFields().getAssignee().getEmailAddress(), taskNameWithLink + CREATION_MESSAGE);
        }
    }

    private Long secondsSinceLastComment(Task task, Comment lastComment, LocalDateTime currentTime) {
        log.info("Last comment of {} is null", task.getDescription());

        ZoneId currentZoneId = ZoneId.systemDefault();
        Date commentCreatedDate = lastComment.getCreatedDate();
        Instant instant = commentCreatedDate.toInstant();
        LocalDateTime lastCommentDateTime = instant.atZone(currentZoneId).toLocalDateTime();

        long durationBetweenNowAndLastCommentCreation = Duration.between(lastCommentDateTime, currentTime).toMillis();

        return durationBetweenNowAndLastCommentCreation;
    }

    private Long secondsSinceIssueCreated(Task task, LocalDateTime currentTime) {
        String taskCreatedDate = task.getFields().getCreatedDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
        LocalDateTime creationDateTime = LocalDateTime.parse(taskCreatedDate, formatter);

        long secondsBetweenNowAndTaskCreation =
                Duration.between(creationDateTime, currentTime).toMillis();

        return secondsBetweenNowAndTaskCreation;
    }

    private boolean shouldSendNoCommentWarning(Long secondsPassedSinceLastComment) {
        return secondsPassedSinceLastComment >= SIX_HOURS;
    }

    private boolean shouldSendTicketStillOpenWarning(Long secondsSinceTicketWasCreated) {
        return secondsSinceTicketWasCreated >= ONE_DAY;
    }
}
