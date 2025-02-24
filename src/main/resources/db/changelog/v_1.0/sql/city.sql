create table city
(
    ID          int auto_increment primary key,
    Name        char(35) default '' not null,
    CountryCode char(3)  default '' not null,
    District    char(20) default '' not null,
    Population  int      default 0  not null,
    constraint city_ibfk_1
        foreign key (CountryCode) references country (Code)
);

create index CountryCode
    on city (CountryCode);