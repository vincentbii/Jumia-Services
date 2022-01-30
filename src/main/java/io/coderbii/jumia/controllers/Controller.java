package io.coderbii.jumia.controllers;

import io.coderbii.jumia.models.Customer;
import io.coderbii.jumia.repos.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final CustomerService customerService;

    public Controller(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param state
     * @param country
     * @param sortBy
     * @return list of customers
     * <p>
     * input format
     * -----------------
     * GET http://localhost:8080/api/customers?pageNo=0&pageSize=20
     * <p>
     * output format
     * -------------------
     * [
     * {
     * "id": 0,
     * "name": "Walid Hammadi",
     * "phone": "(212) 6007989253",
     * "country": null,
     * "state": "invalid",
     * "countryCode": null
     * }
     * ]
     */
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(defaultValue = "") String state,
            @RequestParam(defaultValue = "") String country,
            @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(customerService.getAllCustomers(pageNo, pageSize, sortBy, state, country), new HttpHeaders(), HttpStatus.OK);
    }
}
