--CREATE SEQUENCE tablename_colname_seq;
--CREATE TABLE tablename (
--    colname integer NOT NULL DEFAULT nextval('tablename_colname_seq')
--);
--ALTER SEQUENCE tablename_colname_seq OWNED BY tablename.colname;

CREATE TABLE IF NOT EXISTS accounts (
  account_id INTEGER PRIMARY KEY,
  user_id INTEGER,
  balance DOUBLE PRECISION,
  income DOUBLE PRECISION,
  expense DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS transactions (
  transaction_id INTEGER PRIMARY KEY,
  account_id INTEGER,
  transaction_value DOUBLE PRECISION,
  created_at timestamp
);

CREATE TABLE IF NOT EXISTS categories (
  category_id INTEGER PRIMARY KEY,
  name text,
  type INTEGER
);

CREATE TABLE IF NOT EXISTS users (
  user_id INTEGER PRIMARY KEY,
  name text,
  email text
);
