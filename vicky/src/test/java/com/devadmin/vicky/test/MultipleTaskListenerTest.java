package com.devadmin.vicky.test;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.TaskEventModelType;
import com.devadmin.vicky.controller.jira.model.JiraChangeLogItemModel;
import com.devadmin.vicky.listener.AtReferenceListener;
import com.devadmin.vicky.listener.LabeledTaskListener;
import com.devadmin.vicky.listener.PMOnAssignListener;
import com.devadmin.vicky.listener.ProjectTaskListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link ProjectTaskListener}
 */
public class MultipleTaskListenerTest extends TaskListenerTest {

    /**
     * tests that the event was sent
     */
    @Test
    public void basicTest() {

     createContext();

        TestTaskEventModel testEventModel = getTestTaskEventModel(TaskEventModelType.CREATED);
        publish(testEventModel);

        assertTrue(messageService.channelMessaged);

    }

    // private methods

    private TestTaskEventModel getTestTaskEventModel(TaskEventModelType type) {

        TestTaskEventModel testEventModel = new TestTaskEventModel();
        testEventModel.setType(type);


        //  from PMOnAssign test
        TestJiraChangeLogItem item = new TestJiraChangeLogItem();
        item.setField("assignee");
        item.setTo("testUser");

        List<ChangeLogItem> itemList = new ArrayList<>();
        itemList.add(item);

        TestChangelog testChangelog = new TestChangelog();
        testChangelog.setItems(itemList);
        testEventModel.setChangelog(testChangelog);

        // from labeled
        List<String> labels = Arrays.asList("label1", "label2");
        TestTask task = new TestTask();
        task.setLabels(labels);
        testEventModel.setTask(task);

        return testEventModel;
    }

    private void createContext () {
        ProjectTaskListener projectTaskListener = new ProjectTaskListener(messageService);
        PMOnAssignListener pmOnAssignListener = new PMOnAssignListener(messageService);
        AtReferenceListener atReferenceListener = new AtReferenceListener(messageService);
        LabeledTaskListener labeledTaskListener = new LabeledTaskListener(messageService);
        context.addApplicationListener(projectTaskListener);
        context.addApplicationListener(pmOnAssignListener);
        context.addApplicationListener(atReferenceListener);
        context.addApplicationListener(labeledTaskListener);
        context.refresh();
    }

}

