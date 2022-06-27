CREATE TABLE products (
    id                      VARCHAR(36)  PRIMARY KEY NOT NULL,
    product_name            VARCHAR(50)  NOT NULL,
    product_description     VARCHAR(255) NOT NULL,
    created_at              TIMESTAMP
);