create if not exists user {
       id primary key  auto_increment,
       first_name varchar(255) not null,
       last_name varchar(255) not null,
       email varchar(255) not null,
       password varchar(255) not null
}