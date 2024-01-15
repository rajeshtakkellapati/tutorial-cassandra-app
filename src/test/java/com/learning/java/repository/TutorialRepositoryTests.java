package com.learning.java.repository;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.learning.java.model.Tutorial;
import com.learning.java.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataCassandraTest
class TutorialRepositoryTests {
    @Autowired
    TutorialRepository tutorialRepository;

    private Tutorial tutorial;

    @BeforeEach
    void setUp() {
        tutorial = new Tutorial(Uuids.timeBased(), "Test tutorial","Test Description", true);
    }

    @Test
    void shouldSaveNewTutorial() {
        tutorial = tutorialRepository.save(tutorial);
        assertThat(tutorialRepository.findById(tutorial.getId())).contains(tutorial);
    }
}
