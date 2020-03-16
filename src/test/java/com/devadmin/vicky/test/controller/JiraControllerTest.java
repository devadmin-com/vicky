package com.devadmin.vicky.test.controller;

import com.devadmin.vicky.controller.jira.JiraController;
import net.rcarz.jiraclient.Comment;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static java.nio.file.Files.readAllBytes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.ResourceUtils.getFile;

/**
 * Test {@link JiraController}.
 */
@WebMvcTest(JiraController.class)
@RunWith(SpringRunner.class)
public final class JiraControllerTest {

    /**
     * Mocked Jira client.
     */
    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private JiraClient client;

    /**
     * MockMvc.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Init mocks.
     * Creates mocked comment for JiraClient
     *
     * @throws JiraException If failed
     */
    @Before
    public void init() throws JiraException {
        final Comment mock = Mockito.mock(Comment.class, RETURNS_DEEP_STUBS);
        Mockito.when(mock.getAuthor().getDisplayName()).thenReturn("Mr.Lollipop");
        Mockito.when(mock.getBody()).thenReturn("New issue");
        Mockito.when(
                this.client
                        .getIssue(any())
                        .getComments()
        ).thenReturn(Collections.singletonList(mock));
    }

    /**
     * Test created task.
     *
     * @throws Exception If failed
     */
    @Test
    public void testCreatedTask() throws Exception {
        this.mockMvc
                .perform(post("/event/jira")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                readAllBytes(
                                        getFile("classpath:json/issue-created.json").toPath()
                                )
                        )
                ).andExpect(status().isOk());
    }
}
