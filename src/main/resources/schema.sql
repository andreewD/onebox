DROP TABLE IF EXISTS PRODUCT;
DROP SEQUENCE IF EXISTS product_seq;
DROP TABLE IF EXISTS CART;

CREATE SEQUENCE product_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE PRODUCT (
  product_seq BIGINT NOT NULL DEFAULT nextval('product_seq'),
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  stock INTEGER NOT NULL
);

CREATE TABLE CART (
  id UUID PRIMARY KEY,
  products VARCHAR(250),
  total DECIMAL(10,2),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
