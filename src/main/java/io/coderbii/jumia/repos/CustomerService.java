package io.coderbii.jumia.repos;

import io.coderbii.jumia.models.Country;
import io.coderbii.jumia.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CountryService countryService;

    public CustomerService(CustomerRepository customerRepository, CountryService countryRepository) {
        this.customerRepository = customerRepository;
        this.countryService = countryRepository;
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param state
     * @param country  gets customers from the database as per the parameters passed
     *                 map phone numbers as per the regex
     *                 determine if phone number is valid or not
     * @returns a list of customers
     */
    public List<Customer> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy, String state, String country) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Customer> pagedResult = customerRepository.findAll(paging);
        Map<String, Country> countriesMap = countryService.getAll();

        if (pagedResult.hasContent()) {
            return pagedResult.getContent().stream()
                    .map(customer -> {
                        try {
                            countryService.setCountryCodeAndName(customer, countriesMap);
                        } catch (Exception exception) {
                            //log/throw an exception
                        }
                        countryService.setState(customer);
                        return customer;
                    })
                    .filter(customer -> phoneHasCountry(country, customer) && phoneIsValid(state, customer))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Validate phone number
     *
     * @param state
     * @param customer
     * @return
     */
    public boolean phoneIsValid(String state, Customer customer) {
        if (state.equals("")) return true;
        return customer.getState().equals(state);
    }

    /**
     * @param country
     * @param customer
     * @return
     */
    public boolean phoneHasCountry(String country, Customer customer) {
        if (country.equals("")) return true;
        return country.equals(customer.getCountry());
    }


}
