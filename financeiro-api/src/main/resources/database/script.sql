USE dbFinanceiro;

CREATE TABLE category (
    id_category INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    color VARCHAR(7) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id_category)
);

CREATE TABLE entry (
	  id_entry INT(11) NOT NULL AUTO_INCREMENT,
    value DECIMAL(7,2) NOT NULL,
    description VARCHAR(45) NOT NULL,
    entryDate DATE NOT NULL,
    CONSTRAINT pk_entry PRIMARY KEY (id_entry)
);

CREATE TABLE entryCategories (
    id_entry INT(11) NOT NULL,
    id_category INT(11) NOT NULL
);

