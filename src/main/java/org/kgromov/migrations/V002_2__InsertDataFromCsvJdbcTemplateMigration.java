package org.kgromov.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
public class V002_2__InsertDataFromCsvJdbcTemplateMigration extends BaseJavaMigration {
    private final JdbcTemplate jdbcTemplate;

    public V002_2__InsertDataFromCsvJdbcTemplateMigration(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void migrate(Context context) {
        var jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
        ClassPathResource csvFile = new ClassPathResource("db/changelog/city.csv");
        try {
            List<String> cityRows = Files.readAllLines(Paths.get(csvFile.getURI())).stream().skip(1).toList();
            jdbcTemplate.batchUpdate(
                    "INSERT INTO city (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)",
                    cityRows,
                    1000,
                    (PreparedStatement ps, String cityRow) -> {
                        String[] cityColumns = cityRow.split(",");
                        ps.setString(1, cityColumns[1]);
                        ps.setString(2, cityColumns[2]);
                        ps.setString(3, cityColumns[3]);
                        ps.setInt(4, Integer.parseInt(cityColumns[4]));
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void migrate2(Context context) {
        var jdbcTemplate = new NamedParameterJdbcTemplate(
                new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
        );
        ClassPathResource csvFile = new ClassPathResource("db/changelog/city.csv");
        try {
            var bindingValues = Files.readAllLines(Paths.get(csvFile.getURI())).stream().skip(1)
                    .map(row -> {
                        String[] cityColumns = row.split(",");
                        return new MapSqlParameterSource(Map.of(
                                "name", cityColumns[1],
                                "countryCode", cityColumns[2],
                                "district", cityColumns[3],
                                "population", Integer.parseInt(cityColumns[4])
                        ));
                    })
                    .toArray(MapSqlParameterSource[]::new);
            jdbcTemplate.batchUpdate(
                    "INSERT INTO city (Name, CountryCode, District, Population) VALUES (:name, :countryCode, :district, :population)",
                    bindingValues
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
