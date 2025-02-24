drop table if exists country;

create table country
(
    Code           char(3)                                                                                      default ''     not null primary key,
    Name           char(52)                                                                                     default ''     not null,
    Continent      enum ('Asia', 'Europe', 'North America', 'Africa', 'Oceania', 'Antarctica', 'South America') default 'Asia' not null,
    Region         char(26)                                                                                     default ''     not null,
    SurfaceArea    decimal(10, 2)                                                                               default 0.00   not null,
    IndepYear      smallint                                                                                                    null,
    Population     int                                                                                          default 0      not null,
    LifeExpectancy decimal(3, 1)                                                                                               null,
    GNP            decimal(10, 2)                                                                                              null,
    GNPOld         decimal(10, 2)                                                                                              null,
    LocalName      char(45)                                                                                     default ''     not null,
    GovernmentForm char(45)                                                                                     default ''     not null,
    HeadOfState    char(60)                                                                                                    null,
    Capital        int                                                                                                         null,
    Code2          char(2)                                                                                      default ''     not null
);