drop SCHEMA if exists exchange_schema cascade;

CREATE SCHEMA exchange_schema;

CREATE TABLE IF NOT EXISTS exchange_schema.exchange
(

    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    login         VARCHAR(255)                            NOT NULL,
    from_currency VARCHAR(255)                            NOT NULL,
    from_value    DECIMAL                                 NOT NULL,
    to_currency   VARCHAR(255)                            NOT NULL,
    to_value      DECIMAL                                 NOT NULL,
    to_login      VARCHAR(255)                            NOT NULL,

    CONSTRAINT pk_exchange PRIMARY KEY (id)
);