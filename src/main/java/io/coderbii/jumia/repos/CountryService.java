package io.coderbii.jumia.repos;

import io.coderbii.jumia.models.Country;
import io.coderbii.jumia.models.Customer;
import io.coderbii.jumia.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class CountryService {
    /**
     * Demo countries list
     * This can be fetched from database or cache
     *
     * @returns a list of demo countries
     */
    public HashMap<String, Country> getAll() {
        HashMap<String, Country> demoCountryList = new HashMap<>();
        demoCountryList.put(Constants.CAMEROON, new Country(1, "Cameroon", Constants.CAMEROON, "237"));
        demoCountryList.put(Constants.ETHIOPIA, new Country(2, "Ethiopia", Constants.ETHIOPIA, "251"));
        demoCountryList.put(Constants.MOROCCO, new Country(3, "Morocco", Constants.MOROCCO, "212"));
        demoCountryList.put(Constants.MOZAMBIQUE, new Country(4, "Mozambique", Constants.MOZAMBIQUE, "258"));
        demoCountryList.put(Constants.UGANDA, new Country(5, "Uganda", Constants.UGANDA, "256"));
        return demoCountryList;
    }


    /**
     * set country name and code as per the regex
     *
     * @param customer
     * @param countriesMap
     * @throws Exception
     */
    public void setCountryCodeAndName(Customer customer, Map<String, Country> countriesMap) throws Exception {
        if (countriesMap.isEmpty())
            throw new Exception("Countries' list cannot be empty");

        String REGEX = "";

        if (Pattern.compile(Constants.CAMEROON).matcher(customer.getPhone()).matches()) {
            REGEX = Constants.CAMEROON;
        } else if (Pattern.compile(Constants.ETHIOPIA).matcher(customer.getPhone()).matches()) {
            REGEX = Constants.ETHIOPIA;
        } else if (Pattern.compile(Constants.MOROCCO).matcher(customer.getPhone()).matches()) {
            REGEX = Constants.MOROCCO;
        } else if (Pattern.compile(Constants.MOZAMBIQUE).matcher(customer.getPhone()).matches()) {
            REGEX = Constants.MOZAMBIQUE;
        } else if (Pattern.compile(Constants.UGANDA).matcher(customer.getPhone()).matches()) {
            REGEX = Constants.UGANDA;
        }

        if (REGEX.isEmpty()) return;

        customer.setCountryCode(countriesMap.get(REGEX).getCode());
        customer.setCountry(countriesMap.get(REGEX).getName());
    }

    /**
     * indicate phone validity (valid | invalid)
     *
     * @param customer
     */
    public void setState(Customer customer) {
        if (customer.getCountry() == null) customer.setState("invalid");
        else customer.setState("valid");
    }
}
