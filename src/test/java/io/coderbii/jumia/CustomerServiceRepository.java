package io.coderbii.jumia;

import io.coderbii.jumia.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerServiceRepository {
    public List<Customer> findAll();
}
