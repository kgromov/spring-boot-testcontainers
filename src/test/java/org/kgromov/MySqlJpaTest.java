package org.kgromov;

import org.junit.jupiter.api.Test;
import org.kgromov.domain.Country;
import org.kgromov.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@MySqlIntegrationTest
@Transactional
class MySqlJpaTest {
    @Autowired private CountryRepository countryRepository;

    @Test
    void testAll() {
        List<Country> countries = countryRepository.findAll();
        countries.forEach(System.out::println);
    }

}
