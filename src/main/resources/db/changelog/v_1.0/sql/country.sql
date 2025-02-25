drop table if exists country;

CREATE TABLE IF NOT EXISTS country (
   Code             VARCHAR(3) NOT NULL PRIMARY KEY,
   Name             VARCHAR(60) NOT NULL,
   Continent        ENUM ('Asia', 'Europe', 'North America', 'Africa', 'Oceania', 'Antarctica', 'South America') NOT NULL,
   Region           VARCHAR(32) NOT NULL,
   SurfaceArea      DECIMAL(10, 2) NOT NULL,
   IndepYear        SMALLINT,
   Population       INT NOT NULL,
   LifeExpectancy   DECIMAL(3, 1),
   GNP              DECIMAL(10, 2),
   GNPOld           DECIMAL(10, 2),
   LocalName        VARCHAR(45) NOT NULL,
   GovernmentForm   VARCHAR(45) NOT NULL,
   HeadOfState      VARCHAR(60),
   Capital          INT,
   Code2            VARCHAR(2) NOT NULL
) engine=InnoDB;