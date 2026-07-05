package org.kgromov;

import org.junit.jupiter.api.Test;
import org.kgromov.domain.Country;
import org.kgromov.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:tc:mysql://mysql:8.0.29:///test"
})
class CountryRepositoryDataJpaTest {
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void findAll_returnsAllCountries() {
        List<Country> countries = countryRepository.findAll();

        assertThat(countries).extracting(Country::getName).isNotNull();
        assertThat(countries).extracting(Country::getCode).allMatch(code -> code.length() == 3);
        assertThat(countries).extracting(Country::getCode2).allMatch(code -> code.length() == 2);
    }


    @Test
    void count_ReturnsAllCountriesCount() {
        long countries = countryRepository.count();

        assertThat(countries).isEqualTo(239);
    }

}
