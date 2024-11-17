CREATE TABLE farm
(
    id            UUID             NOT NULL,
    name          VARCHAR(255)     NOT NULL,
    location      VARCHAR(255)     NOT NULL,
    area          DOUBLE PRECISION NOT NULL,
    creation_date date             NOT NULL,
    CONSTRAINT pk_farm PRIMARY KEY (id)
);
CREATE TABLE field
(
    id        UUID    NOT NULL,
    area      FLOAT   NOT NULL,
    max_trees INTEGER NOT NULL,
    farm_id   UUID    NOT NULL,
    CONSTRAINT pk_field PRIMARY KEY (id)
);

ALTER TABLE field
    ADD CONSTRAINT FK_FIELD_ON_FARM FOREIGN KEY (farm_id) REFERENCES farm (id);
CREATE TABLE tree
(
    id            UUID NOT NULL,
    planting_date date,
    field_id      UUID NOT NULL,
    CONSTRAINT pk_tree PRIMARY KEY (id)
);

ALTER TABLE tree
    ADD CONSTRAINT FK_TREE_ON_FIELD FOREIGN KEY (field_id) REFERENCES field (id);