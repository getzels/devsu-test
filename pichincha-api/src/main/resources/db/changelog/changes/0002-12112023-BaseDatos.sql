-- Create 'person' table
CREATE TABLE IF NOT EXISTS person
(
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    gender VARCHAR(50),
    age    INTEGER,
    identification VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(15)
);

-- Create 'client' table that inherits from 'person'
CREATE TABLE IF NOT EXISTS client
(
    id        BIGINT PRIMARY KEY REFERENCES person (id),
    client_id VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255),
    active    BOOLEAN      NOT NULL
);

-- Create 'account' table
CREATE TABLE IF NOT EXISTS account
(
    id             BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20)    NOT NULL UNIQUE,
    account_type   VARCHAR(50)    NOT NULL,
    initial_balance  NUMERIC(15, 2) NOT NULL,
    status          VARCHAR(15),
    client_id      BIGINT REFERENCES client (id)
);

-- Create 'transaction' table
CREATE TABLE IF NOT EXISTS transaction
(
    id         BIGSERIAL PRIMARY KEY,
    date       TIMESTAMP      NOT NULL,
    account_id BIGINT REFERENCES account (id),
    type       VARCHAR(50)    NOT NULL,
    amount     NUMERIC(15, 2) NOT NULL,
    balance_after_transaction    NUMERIC(15, 2) NOT NULL
);
