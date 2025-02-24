create table countrylanguage
(
    CountryCode char(3)         default ''  not null,
    Language    char(30)        default ''  not null,
    IsOfficial  enum ('T', 'F') default 'F' not null,
    Percentage  decimal(4, 1)   default 0.0 not null,
    primary key (CountryCode, Language),
    constraint countryLanguage_ibfk_1
        foreign key (CountryCode) references country (Code)
);

create index CountryCode
    on countrylanguage (CountryCode);