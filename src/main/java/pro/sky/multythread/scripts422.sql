create table person
(
    id             integer primary key,
    name           varchar,
    age            integer,
    drivingLicense boolean,
    car_id         integer references car (id) not null
);

create table car
(
    id    integer primary key,
    brand varchar,
    model varchar,
    price integer
);