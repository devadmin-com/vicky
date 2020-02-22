/*
 * Copyright (c) http://devadmin.com
 *
 * License: https://github.com/devadmin-com/vicky/blob/master/LICENSE
 */

package com.devadmin.vicky.format;

import com.devadmin.vicky.model.jira.task.TaskEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Implements formatting of assign @TaskEventModelWrapper for sending to a @MessageService
 */
@Component("SummaryFormatter")
public class SummaryTaskEventFormatter extends SimpleTaskEventFormatter {

    @Override
    public String format(TaskEvent event) {
        final String lastComment = super.getLastComment(event);
        if (StringUtils.isNotBlank(lastComment) && !lastComment.equals(EMPTY_COMMENT)) {
            return String.format(
                    "%s %s %s ➠ %s",
                    super.formatBase(event),
                    super.getShortDescription(event),
                    super.getLastCommenter(event),
                    super.getLastComment(event));
        } else {//task without comment
            return String.format(
                    "%s %s ",
                    super.formatBase(event),
                    super.getShortDescription(event)
            );
        }
    }
}
