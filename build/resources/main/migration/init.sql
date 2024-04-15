CREATE DATABASE IF NOT EXISTS dict_dut;

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       login VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);