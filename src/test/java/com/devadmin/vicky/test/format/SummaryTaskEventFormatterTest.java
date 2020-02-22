package com.devadmin.vicky.test.format;

import com.devadmin.vicky.config.FormatConfig;
import com.devadmin.vicky.format.SummaryTaskEventFormatter;
import com.devadmin.vicky.format.TaskEventFormatter;
import com.devadmin.vicky.model.jira.JiraEventModel;
import com.devadmin.vicky.test.TestTasks;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * Test class for {@link com.devadmin.vicky.format.SummaryTaskEventFormatter}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                FormatConfig.class,
                SummaryTaskEventFormatter.class,
        }
)
public class SummaryTaskEventFormatterTest {

    /**
     * Formatter to use.
     */
    @Autowired
    @Qualifier("SummaryFormatter")
    private TaskEventFormatter taskEventFormatter;

    /**
     * Mock properties.
     */
    @MockBean(name = "issueTypeIdToIconsMapping")
    private Properties properties;


    /**
     * Init.
     */
    @Before
    public void init() {
        Mockito.when(this.properties.getProperty(TestTasks.TEST_ID))
                .thenReturn("test icon");
    }

    /**
     * Test correct format.
     */
    @Test
    public void testFormat() {
        final JiraEventModel jiraEventModel = TestTasks.taskModel("lollipop", "fin", "Hello world!");
        Assert.assertThat(
                this.taskEventFormatter.format(jiraEventModel),
                CoreMatchers.is(
                        String.join(
                                "",
                                "test icon <https://devadmin.atlassian.net/browse/test key | test key> Resolved 解決済: ",
                                "Everything is ok @fin Bla bla lollipop ➠ Hello world!"
                        )
                )
        );
    }

    /**
     * Test correct format.
     */
    @Test
    public void testFormatWithoutComment() {
        final JiraEventModel jiraEventModel = TestTasks.taskModel("lollipop", "fin", "Hello world!");
        jiraEventModel.setComment(null);
        Assert.assertThat(
                this.taskEventFormatter.format(jiraEventModel),
                CoreMatchers.is(
                        String.join(
                                "",
                                "test icon <https://devadmin.atlassian.net/browse/test key | ",
                                "test key> Resolved 解決済: Everything is ok @fin Bla bla "
                        )
                )
        );
    }
}
