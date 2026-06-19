package org.kgromov.migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Profile("imperative")
@Component
public class V004__InsertDataFromCsvJdbcVanillaMigration extends BaseJavaMigration {

    @Override
    public void migrate(Context context) {
        ClassPathResource csvFile = new ClassPathResource("db/changelog/countrylanguage.csv");
        String insertSql = "INSERT INTO countrylanguage (CountryCode, Language, IsOfficial, Percentage) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = context.getConnection().prepareStatement(insertSql)) {
            Files.readAllLines(Paths.get(csvFile.getURI())).stream().skip(1)
                    .map(line -> line.split(","))
                    .forEach(cityColumns -> {
                        try {
                            ps.setString(1, cityColumns[1]);
                            ps.setString(2, cityColumns[2]);
                            ps.setString(3, cityColumns[3]);
                            ps.setBigDecimal(4, new BigDecimal(cityColumns[4]));
                            ps.addBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
            ps.executeBatch();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
