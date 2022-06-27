create table licenses
(
  id                VARCHAR(36)  PRIMARY KEY,
  license_name      VARCHAR(64)  NOT NULL UNIQUE ,
  description       VARCHAR(256) NOT NULL,
  username          VARCHAR(64)  NOT NULL UNIQUE,
  password          VARCHAR(64)  NOT NULL,
  product_id        VARCHAR(36)  NOT NULL,

  foreign key (product_id) references products(id)
);