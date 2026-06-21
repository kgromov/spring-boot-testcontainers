# Spring Boot Testcontainers

A demo Spring Boot application showcasing **Testcontainers** integration with **MySQL**, **Flyway** database migrations (SQL and Java-based), and **Spring Data JPA**. The project uses the classic [World database](https://dev.mysql.com/doc/world-setup/en/) schema (`country`, `city`, `countrylanguage`) as sample data.

## Tech Stack

- **Java 21**
- **Spring Boot 3.4.3**
  - Spring Data JPA
  - Spring Web
  - Spring Boot Testcontainers
- **MySQL 8** (via `mysql-connector-j`)
- **Flyway** (Core + MySQL) — schema & data migrations
- **Testcontainers** (JUnit Jupiter + MySQL module)
- **Lombok**
- **Maven** (with Maven Wrapper)

## Project Structure

```
src/main/java/org/kgromov/
├── SpringBootTestcontainersApplication.java   # main entry point
├── domain/                                     # JPA entities
│   ├── City.java
│   ├── Country.java
│   ├── CountryLanguage.java
│   └── LanguageCode.java                       # @Embeddable composite key
├── repository/                                 # Spring Data JPA repositories
│   ├── CityRepository.java
│   ├── CountryRepository.java
│   └── CountryLanguageRepository.java
└── migrations/                                 # Java-based Flyway migrations
    ├── LogCallback.java
    ├── V3__InsertDataFromCsvJdbcTemplateMigration.java
    └── V4__InsertDataFromCsvJdbcVanillaMigration.java

src/main/resources/
├── application.properties
└── db/changelog/
    ├── V1__Create_tables.sql                   # creates country, city, countrylanguage
    ├── V2__Insert-data.sql                      # loads country.csv via LOAD DATA LOCAL INFILE
    └── data/
        ├── country.csv
        ├── city.csv
        └── countrylanguage.csv

src/test/java/org/kgromov/
├── MySqlIntegrationTest.java                   # @MySqlIntegrationTest meta-annotation
├── MysqlTestcontainersConfiguration.java        # @ServiceConnection MySQLContainer
├── MySqlJpaTest.java                            # sample JPA repository test
├── PopulateCityWithCsvDataTest.java             # tests the CSV Java migration
└── TestSpringBootTestcontainersApplication.java # local dev launcher with Testcontainers
```

## Domain Model

| Entity | Table | Notes |
|---|---|---|
| `Country` | `country` | Primary key is the 3-letter ISO code (`Code`) |
| `City` | `city` | `@ManyToOne` to `Country`, lazy-loaded, with `@JsonIgnoreProperties` to avoid serializing the full country payload |
| `CountryLanguage` | `countrylanguage` | Composite key via `@EmbeddedId LanguageCode` (country code + language) |

## Database Migrations

Flyway manages the schema under `classpath:db/changelog`:

| Version | Type | Description |
|---|---|---|
| `V1__Create_tables.sql` | SQL | Creates `country`, `city`, `countrylanguage` tables |
| `V2__Insert-data.sql` | SQL | Loads `country.csv` via `LOAD DATA LOCAL INFILE` (requires `allowLoadLocalInfile=true`) |
| `V3__InsertDataFromCsvJdbcTemplateMigration` | Java | Loads `city.csv` using `JdbcTemplate.batchUpdate` — active only under the `imperative` profile |
| `V4__InsertDataFromCsvJdbcVanillaMigration` | Java | Loads `countrylanguage.csv` using plain JDBC `PreparedStatement` batching — active only under the `imperative` profile |

> Java migrations (`V3`, `V4`) are Spring `@Component` beans guarded by `@Profile("imperative")`, so they are skipped unless that profile is active. By default, only `V1` and `V2` run.

A `LogCallback` component logs every Flyway lifecycle `Event` for visibility into the migration process.

## Configuration

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/flyway?allowLoadLocalInfile=true
spring.datasource.username=root
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/changelog
spring.flyway.baseline-on-migrate=true
spring.flyway.clean-disabled=true
```

- `ddl-auto=none` — schema is fully owned by Flyway, not Hibernate.
- `allowLoadLocalInfile=true` — required for the `LOAD DATA LOCAL INFILE` statement in `V2`.

## Running Locally

### Prerequisites

- JDK 21
- Docker (running) — required by Testcontainers
- A running MySQL 8 instance on `localhost:3306` **or** use the Testcontainers-backed dev launcher (see below)

### Option 1 — Run against your own MySQL

1. Create a database named `flyway` and ensure local infile loading is enabled on the server.
2. Update credentials in `application.properties` if needed.
3. Start the app:

```bash
./mvnw spring-boot:run
```

### Option 2 — Run with Testcontainers (no local MySQL needed)

Use `TestSpringBootTestcontainersApplication`, which boots the main application wired to a Testcontainers-managed MySQL instance (`MysqlTestcontainersConfiguration`):

```bash
./mvnw test-compile exec:java -Dexec.mainClass=org.kgromov.TestSpringBootTestcontainersApplication -Dexec.classpathScope=test
```

Or simply run the `main` method of `TestSpringBootTestcontainersApplication` from your IDE.

## Testing

Tests are tagged with the `@MySqlIntegrationTest` meta-annotation, which:

- Boots a full Spring context (`@SpringBootTest`, random port)
- Spins up a shared, reusable MySQL 8.0.29 container via `@ImportTestcontainers(MysqlTestcontainersConfiguration.class)`
- Skips automatically if Docker is unavailable (`disabledWithoutDocker = true`)
- Is disabled in native image / AOT test modes

Run all tests:

```bash
./mvnw test
```

Run a specific test:

```bash
./mvnw test -Dtest=MySqlJpaTest
./mvnw test -Dtest=PopulateCityWithCsvDataTest
```

`PopulateCityWithCsvDataTest` specifically verifies the `V3` Java migration by running Flyway up to version `3` and asserting that all 4,079 cities from `city.csv` were inserted correctly.

## Building

```bash
./mvnw clean package
```

This produces an executable jar in `target/`.

## Notes

- `spring-boot-devtools` and `spring-boot-configuration-processor` are optional dependencies for local development convenience.
- Lombok is excluded from the final fat jar via the `spring-boot-maven-plugin` configuration.
- The MySQL Testcontainers instance is configured with `withReuse(true)` to speed up repeated local test runs.
