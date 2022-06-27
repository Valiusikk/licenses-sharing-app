CREATE TABLE orders_licenses
(
    order_id   VARCHAR(36) REFERENCES orders (id),
    license_id VARCHAR(36) REFERENCES licenses (id),
    CONSTRAINT order_license_PK PRIMARY KEY (order_id, license_id)
)