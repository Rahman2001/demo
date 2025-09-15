CREATE TABLE IF NOT EXISTS "note" (
    id int primary key ,
    author_id int,
    raw_text varchar(100),
    summary varchar(100),
    status varchar(30),
    timestamp timestamp,
    foreign key (author_id) references "users" (id)
)