package io.coderbii.jumia;

import static org.assertj.core.api.Assertions.assertThat;

import io.coderbii.jumia.controllers.HomeController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeControllerTest {
    @Autowired
    private HomeController controller;

    @Test
    public void index() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testIndex() {
    }
}