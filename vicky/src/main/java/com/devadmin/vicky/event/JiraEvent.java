package com.devadmin.vicky.event;

import com.devadmin.vicky.controller.model.jira.JiraEventModel;
import org.springframework.context.ApplicationEvent;

public class JiraEvent extends ApplicationEvent {

  private final JiraEventModel jiraEventModel;

  /**
   * Create a new Jira Event.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   * @param jiraEventModel the event which is occurred
   */
  public JiraEvent(Object source, JiraEventModel jiraEventModel) {
    super(source);
    this.jiraEventModel = jiraEventModel;
  }

  /**
   * @return the event which is occurred
   */
  JiraEventModel getJiraEventModel() {
    return jiraEventModel;
  }
}
