CREATE TABLE IF NOT EXISTS "users" (
    id int primary key,
    username varchar(50),
    first_name varchar(20),
    last_name varchar(20),
    password varchar(300),
    role varchar(50)
);

INSERT INTO "users" (id, username, first_name, last_name, password, role)
VALUES (1, 'rahman.rjpv', 'Rahman', 'Rejepov', 'mypassword', 'ADMIN'),
       (2, 'mert.ozdm', 'Mert', 'Ozdemir', 'mypassword2', 'AGENT'),
       (3, 'ahmet.dmrl', 'Ahmet', 'Demirel', 'mypassword3', 'AGENT');