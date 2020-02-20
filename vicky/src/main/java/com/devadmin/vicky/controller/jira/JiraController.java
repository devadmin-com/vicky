/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.controller.jira;

import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import com.devadmin.vicky.event.TaskEventModelWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class contain methods which receive events from JIRA
 */
@RestController
@AllArgsConstructor
@RequestMapping("event")
@Slf4j
public class JiraController {

    private final ApplicationEventPublisher applicationEventPublisher;

    private final JiraClient jiraClient;

    /**
     * Endpoints which handles event from jir to path "/jira"
     */
    @PostMapping("/jira")
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity jiraEvent(@RequestBody JiraEventModel jiraEventModel) {
        log.info("Accept request with task : {}", jiraEventModel.getTask().getDescription());

        setLastComment(jiraEventModel);

        switch (jiraEventModel.getWebhookEvent()) {
            case "jira:issue_created":
                jiraEventModel.setType(TaskEventType.CREATED);
                break;

            case "jira:issue_updated":
                jiraEventModel.setType(TaskEventType.UPDATED);
                break;

            case "comment_created":/*Do nothing*/
            case "comment_updated":
                jiraEventModel.setType(TaskEventType.COMMENT);
                break;

            default:
                jiraEventModel.setType(TaskEventType.DEFAULT);
                break;
        }

        final TaskEventModelWrapper event = new TaskEventModelWrapper(jiraEventModel);
        applicationEventPublisher.publishEvent(event);

        return ResponseEntity.ok().build();
    }

    /**
     * We are getting issue event we don't have comments in it so we need to get it with jiraClient by
     * issueId and last comment to jiraEventModel
     */
    private void setLastComment(JiraEventModel jiraEventModel) {
        IssueModel task = jiraEventModel.getTask();
        try {
            List<Comment> comments = jiraClient.getIssue(task.getId()).getComments();
            if (!comments.isEmpty()) {
                Comment comment = comments.get(comments.size() - 1);
                CommentModel lastComment = convertCommentToCommentModel(comment);
                task.setLastComment(lastComment);
            } else {
                task.setLastComment(getNoCommentModel());
            }
        } catch (JiraException e) {
            log.error("Failed to retrieve issue by issueId: " + task.getId(), e);
        }

    }

    /**
     * @return default message if there is no comment
     */
    private CommentModel getNoCommentModel() {
        CommentModel commentModel = new CommentModel();
        AuthorModel authorModel = new AuthorModel();

        authorModel.setDisplayName("Vicky");
        commentModel.setAuthor(authorModel);
        commentModel.setBody(" ");
        return commentModel;
    }

    // mapping to comment model to set to task
    private CommentModel convertCommentToCommentModel(Comment comment) {
        CommentModel commentModel = new CommentModel();
        AuthorModel authorModel = new AuthorModel();

        authorModel.setDisplayName(comment.getAuthor().getDisplayName());
        commentModel.setAuthor(authorModel);
        commentModel.setBody(comment.getBody());
        return commentModel;
    }
}
