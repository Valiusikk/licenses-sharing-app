CREATE TABLE orders
(
    id         VARCHAR(36) PRIMARY KEY,
    user_id    VARCHAR(36) REFERENCES users (id),
    start_date DATE,
    end_date   DATE CHECK (end_date >= start_date)
)