# Persons schema

# --- !Ups
CREATE TABLE persons (
    id int(11) NOT NULL AUTO_INCREMENT,
    firstname varchar(255) NOT NULL,
    infix varchar(50) NOT NULL,
    lastname varchar(255) NOT NULL,
    PRIMARY KEY(id)
);


# --- !Downs
DROP TABLE persons;