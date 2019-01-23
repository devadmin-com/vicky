package com.devadmin.vicky.controller.jira;

import com.devadmin.jira.Comment;
import com.devadmin.jira.JiraClient;
import com.devadmin.jira.JiraException;
import com.devadmin.vicky.TaskEventType;
import com.devadmin.vicky.controller.jira.model.AuthorModel;
import com.devadmin.vicky.controller.jira.model.CommentModel;
import com.devadmin.vicky.controller.jira.model.IssueModel;
import com.devadmin.vicky.controller.jira.model.JiraEventModel;
import com.devadmin.vicky.event.TaskEventModelWrapper;
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

/**
 * This class contain methods which receive events from JIRA
 */
@RestController
@RequestMapping("event")
public class JiraController {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraController.class);

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private JiraClient jiraClient;

  @Autowired
  public JiraController(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @PostMapping("/jira")
  public ResponseEntity jiraEvent(@RequestBody JiraEventModel jiraEventModel) {

    setLastComment(jiraEventModel);

    switch(jiraEventModel.getWebhookEvent()) {

      case "jira:issue_created" :
        jiraEventModel.setType(TaskEventType.CREATED);
        break;

      case "jira:issue_updated" :
        jiraEventModel.setType(TaskEventType.UPDATED);
        break;

      case "comment_created" :
      case "comment_updated" :
        jiraEventModel.setType(TaskEventType.COMMENT);
        break;
        //TODO what if it's something else? should check and throw exception...
        // TODO why to have default method and type on TaskEventType
    }

    final TaskEventModelWrapper event = new TaskEventModelWrapper(jiraEventModel);
    applicationEventPublisher.publishEvent(event);

    return ResponseEntity.ok().build();

  }

  /**
   * set last comment to jiraEventModel (which doesn't contain it by default)
   * TODO: write clear description?
   */
  private void setLastComment(JiraEventModel jiraEventModel) {
    List<Comment> comments = null;
    IssueModel task = jiraEventModel.getTask();
    try {
      comments = jiraClient.getIssue(task.getId()).getComments();
      if (comments.size() > 0){
        Comment comment = comments.get(comments.size() - 1);
        CommentModel lastComment = convertCommentToCommentModel(comment);
        task.setLastComment(lastComment);
      } else {
        task.setLastComment(getDefaultCommentModel());
      }
    } catch (JiraException e) {
      LOGGER.error("Failed to retrieve issue by issueId: " + task.getId(), e);
    }
  }

  /**
   * @return default message if there is no comment
   */
  private CommentModel getDefaultCommentModel() {
    CommentModel commentModel = new CommentModel();
    AuthorModel authorModel = new AuthorModel();

    authorModel.setDisplayName("Vicky");
    commentModel.setAuthor(authorModel);
    commentModel.setBody("This task does not contain comment");
    return commentModel;
  }

  private CommentModel convertCommentToCommentModel(Comment comment) {
    CommentModel commentModel = new CommentModel();
    AuthorModel authorModel = new AuthorModel();

    authorModel.setDisplayName(comment.getAuthor().getDisplayName());
    commentModel.setAuthor(authorModel);
    commentModel.setBody(comment.getBody());
    return commentModel;
  }
}
