CREATE TABLE IF NOT EXISTS ORBITAL_LAUNCHERS
(
    id        int auto_increment unique,
    name      varchar not null,
    code_name varchar not null unique,
    max_leo   int
);

CREATE TABLE IF NOT EXISTS ORBITAL_LAUNCHES
(
    id               int auto_increment unique,
    orbital_launcher int,
    foreign key (orbital_launcher) references ORBITAL_LAUNCHERS (id),
    payload_weight   int,
    launch_date      date
);
