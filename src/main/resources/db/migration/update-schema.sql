CREATE TABLE farm
(
    id            UUID             NOT NULL,
    name          VARCHAR(255)     NOT NULL,
    location      VARCHAR(255)     NOT NULL,
    area          DOUBLE PRECISION NOT NULL,
    creation_date date             NOT NULL,
    CONSTRAINT pk_farm PRIMARY KEY (id)
);