-- V002_2__Insert-data.sql
LOAD DATA LOCAL INFILE './src/main/resources/db/changelog/data/country.csv'
INTO TABLE country
     FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES; -- Skips the header row

LOAD DATA LOCAL INFILE './src/main/resources/db/changelog/data/city.csv'
INTO TABLE city
     FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE './src/main/resources/db/changelog/data/countrylanguage.csv'
INTO TABLE countrylanguage
     FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;
