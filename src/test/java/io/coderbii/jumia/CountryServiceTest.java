package io.coderbii.jumia;

import io.coderbii.jumia.models.Customer;
import io.coderbii.jumia.repos.CountryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    void getAll() {
        Assertions.assertThat(countryService.getAll()).isNotEmpty();
    }

    @Test
    void setCountryCodeAndName() {
        Customer customerInput = new Customer();
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> countryService.setCountryCodeAndName(customerInput, new HashMap<>()));
    }

    @Test
    void setState() {
        Customer customerInvalidStateInput = new Customer();
        countryService.setState(customerInvalidStateInput);
        assertThat(customerInvalidStateInput.getState()).isEqualTo("invalid");

        Customer customerValidStateInput = new Customer();
        customerValidStateInput.setCountry("Cameroon");
        countryService.setState(customerValidStateInput);
        assertThat(customerValidStateInput.getState()).isEqualTo("valid");
    }
}