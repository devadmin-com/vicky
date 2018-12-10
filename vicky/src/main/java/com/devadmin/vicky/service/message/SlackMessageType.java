package com.devadmin.vicky.service.message;

/**
 * This enum contain the type of Slack messages
 */
public enum  SlackMessageType {
  ISSUE {
    @Override
    public String createMessage(SlackMessageEntity messageEntity) {
      return String.format("%s <%s | %s> %s: %s @%s\n %s ➠ %s",
          messageEntity.getIssueTypeIcon(),
          messageEntity.getIssueUrl(),
          messageEntity.getIssueKey(),
          messageEntity.getIssueStatus(),
          messageEntity.getIssueSummary(),
          messageEntity.getIssueAssignedTo(),
          messageEntity.getIssueCommenter(),
          messageEntity.getIssueComment());
    }
  },
  ASSIGNEE {
    @Override
    public String createMessage(SlackMessageEntity messageEntity) {
      return String.format("%s assigned to you: %s <%s | %s> %s: %s @%s\n %s ➠ %s",
          messageEntity.getIssueAssignedFrom(),
          messageEntity.getIssueTypeIcon(),
          messageEntity.getIssueUrl(),
          messageEntity.getIssueKey(),
          messageEntity.getIssueStatus(),
          messageEntity.getIssueSummary(),
          messageEntity.getIssueAssignedTo(),
          messageEntity.getIssueCommenter(),
          messageEntity.getIssueComment());
    }
  },
  COMMENT {
    @Override
    public String createMessage(SlackMessageEntity messageEntity) {
      return String.format("%s <%s | %s> %s: %s\n %s ➠ %s",
          messageEntity.getIssueTypeIcon(),
          messageEntity.getIssueUrl(),
          messageEntity.getIssueKey(),
          messageEntity.getIssueStatus(),
          messageEntity.getIssueSummary(),
          messageEntity.getIssueCommenter(),
          messageEntity.getIssueComment());
    }
  },
  ISSUENUMBER {
    @Override
    public String createMessage(SlackMessageEntity messageEntity) {
      return String.format("%s <%s | %s> %s: %s\n %s \n%s ➠ %s",
          messageEntity.getIssueTypeIcon(),
          messageEntity.getIssueUrl(),
          messageEntity.getIssueKey(),
          messageEntity.getIssueStatus(),
          messageEntity.getIssueSummary(),
          messageEntity.getIssueDescription(),
          messageEntity.getIssueCommenter(),
          messageEntity.getIssueComment());
    }
  };

  public abstract String createMessage(SlackMessageEntity messageEntity);
}
