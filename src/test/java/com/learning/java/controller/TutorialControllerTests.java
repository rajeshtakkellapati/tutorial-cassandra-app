package com.learning.java.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.java.model.Tutorial;
import com.learning.java.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(value = TutorialController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class TutorialControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TutorialRepository tutorialRepository;

    private Tutorial tutorial;

    @BeforeEach
    void setUp() {
        tutorial = new Tutorial(Uuids.timeBased(), "Test tutorial","Test Description", true);
    }
    @Test
    void shouldSaveNewTutorial() throws Exception {
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(tutorial);
        mvc.perform(post("/api/tutorials").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(tutorial)))
                .andExpect(status().isCreated());
        verify(tutorialRepository, VerificationModeFactory.times(1)).save(any(Tutorial.class));
        reset(tutorialRepository);
    }
}
