package io.coderbii.jumia;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.coderbii.jumia.controllers.Controller;
import io.coderbii.jumia.repos.CustomerService;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

@WebMvcTest(Controller.class)
class CustomerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Autowired
    private CustomerService customerService ;

//    CustomerServiceTest(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    @Test
    void getAllCustomers() throws Exception {
        when(service.getAllCustomers(0, 10, "", "", "")).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/customers")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }

    @Test
    void phoneHasCountry() {
    }

    @Test
    void phoneIsValid() {

    }
}