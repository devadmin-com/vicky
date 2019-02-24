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
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** This class contain methods which receive events from JIRA */
@RestController
@RequestMapping("event")
public class JiraController {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraController.class);

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired private JiraClient jiraClient;

  @Autowired
  public JiraController(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  /** Endpoints which handles event from jir to path "/jira" */
  @PostMapping("/jira")
  public ResponseEntity jiraEvent(@RequestBody JiraEventModel jiraEventModel) {

    setLastComment(jiraEventModel);

    switch (jiraEventModel.getWebhookEvent()) {
      case "jira:issue_created":
        jiraEventModel.setType(TaskEventType.CREATED);
        break;

      case "jira:issue_updated":
        jiraEventModel.setType(TaskEventType.UPDATED);
        break;

      case "comment_created":
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
    List<Comment> comments = null;
    IssueModel task = jiraEventModel.getTask();
    try {
      comments = jiraClient.getIssue(task.getId()).getComments();
      if (comments.size() > 0) {
        Comment comment = comments.get(comments.size() - 1);
        CommentModel lastComment = convertCommentToCommentModel(comment);
        task.setLastComment(lastComment);
      } else {
        task.setLastComment(getNoCommentModel());
      }
    } catch (JiraException e) {
      LOGGER.error("Failed to retrieve issue by issueId: " + task.getId(), e);
    }
  }

  /** @return default message if there is no comment */
  private CommentModel getNoCommentModel() {
    CommentModel commentModel = new CommentModel();
    AuthorModel authorModel = new AuthorModel();

    authorModel.setDisplayName("Vicky");
    commentModel.setAuthor(authorModel);
    commentModel.setBody("NO COMMENT - This task does not contain a comment");
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
