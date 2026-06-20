package org.kgromov;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("imperative")
@MySqlIntegrationTest
@TestPropertySource(properties = {
        "spring.flyway.target=001.1"
})
public class PopulateCityWithCsvData {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Flyway flyway;

    @Test
    void shouldPopulateDisplayNamesForExistingUsers() {
        List<Map<String, Object>> citiesBefore = jdbcTemplate.queryForList(
                "SELECT Name, CountryCode, District, Population FROM city"
        );
        assertThat(citiesBefore).isEmpty();

        flyway.migrate();

        List<Map<String, Object>> citiesAfter = jdbcTemplate.queryForList(
                "SELECT ID, Name, CountryCode, District, Population FROM city"
        );

        assertThat(citiesAfter).hasSize(4079);
        assertThat(citiesAfter).anySatisfy(city -> {
            assertThat(city.get("ID")).isNotNull();
            assertThat(city.get("Name")).isNotNull();
            assertThat(city.get("CountryCode")).isNotNull();
            assertThat(city.get("District")).isNotNull();
            assertThat(city.get("Population")).isNotNull();
        });
    }
}
