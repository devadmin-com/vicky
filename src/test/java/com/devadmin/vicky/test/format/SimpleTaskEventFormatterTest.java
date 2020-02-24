package com.devadmin.vicky.test.format;


import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.format.SimpleTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.task.Task;
import com.devadmin.vicky.model.jira.task.TaskEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SimpleTaskEventFormatter.class, FormatConfig.class})
public class SimpleTaskEventFormatterTest {

    /**
     * Formatter to test.
     * Use qualifier to avoid IDE warning
     */
    @Autowired
    @Qualifier("SimpleFormatter")
    private TaskEventFormatter simpleTaskEventFormatter;

    @Test
    public void testFormattedEventHasRightIcon() {
        //Arrange
        Task task = mock(Task.class, RETURNS_DEEP_STUBS);
        TaskEvent taskEvent = mock(TaskEvent.class);
        when(task.getTypeId()).thenReturn("13");
        when(taskEvent.getTask()).thenReturn(task);

        //Act + Assert
        assertThat(simpleTaskEventFormatter.format(taskEvent)).isEqualTo("⚡ <null | null> null: null @null\n null ➠ This task does not contain comment");
    }

    @Test
    public void testFormattedEventHasDefaultIcon() {
        //Arrange
        Task task = mock(Task.class, RETURNS_DEEP_STUBS);
        TaskEvent taskEvent = mock(TaskEvent.class);
        when(task.getTypeId()).thenReturn("177");
        when(taskEvent.getTask()).thenReturn(task);
        //Act + Assert
        assertThat(simpleTaskEventFormatter.format(taskEvent)).isEqualTo(":rocket: <null | null> null: null @null\n null ➠ This task does not contain comment");
    }
}
