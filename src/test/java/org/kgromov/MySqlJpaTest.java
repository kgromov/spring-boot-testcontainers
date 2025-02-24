package org.kgromov;

import org.junit.jupiter.api.Test;
import org.kgromov.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@MySqlIntegrationTest
@Transactional
class MySqlJpaTest {
    @Autowired private CountryRepository countryRepository;

    @Test
    void testAll() {
        countryRepository.findAll().forEach(System.out::println);
    }

}
