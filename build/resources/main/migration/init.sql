# docker run --name mariadb -e MYSQL_ROOT_PASSWORD=your_password -p 3307:3306 -d mariadb:latest
CREATE DATABASE IF NOT EXISTS dict_dut;


CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       login VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);