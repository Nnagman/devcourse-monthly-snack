DROP TABLE snack_order_item;
DROP TABLE snack_order;
DROP TABLE snack;
DROP TABLE customer;

CREATE TABLE snack_category
(
    snack_category_id   binary(16)   NOT NULL PRIMARY KEY,
    snack_category_name varchar(100) NOT NULL UNIQUE,
    updated_at          datetime(6)  NOT NULL,
    created_at          datetime(6)  NOT NULL
);

CREATE TABLE snack
(
    snack_id    binary(16)   NOT NULL PRIMARY KEY,
    name        varchar(100) NOT NULL UNIQUE,
    price       int          NOT NULL,
    category_id varchar(100) NOT NULL,
    description varchar(500),
    updated_at  datetime(6)  NOT NULL,
    created_at  datetime(6)  NOT NULL,
    FOREIGN KEY fk_snack_to_snack_category (category_id) REFERENCES snack_category (snack_category_id)
);

CREATE TABLE customer
(
    customer_id binary(16)   NOT NULL PRIMARY KEY,
    name        varchar(50)  NOT NULL,
    email       varchar(100) NOT NULL UNIQUE,
    address     VARCHAR(200) NOT NULL,
    postcode    VARCHAR(200) NOT NULL,
    updated_at  datetime(6)  NOT NULL,
    created_at  datetime(6)  NOT NULL
);

CREATE TABLE snack_order
(
    snack_order_id binary(16)  NOT NULL PRIMARY KEY,
    customer_id    binary(16)  NOT NULL,
    order_status   VARCHAR(50) NOT NULL,
    updated_at     datetime(6) NOT NULL,
    created_at     datetime(6) NOT NULL,
    FOREIGN KEY fk_snack_order_to_customer (customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE snack_order_item
(
    seq            bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    snack_order_id binary(16)  NOT NULL,
    snack_id       binary(16)  NOT NULL,
    updated_at     datetime(6) NOT NULL,
    created_at     datetime(6) NOT NULL,
    FOREIGN KEY fk_snack_order_item_to_snack_order (snack_order_id) REFERENCES snack_order (snack_order_id),
    FOREIGN KEY fk_snack_order_item_to_snack (snack_id) REFERENCES snack (snack_id)
);

