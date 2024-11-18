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
CREATE TABLE harvest
(
    id             UUID             NOT NULL,
    field_id       UUID             NOT NULL,
    harvest_date   date,
    season         VARCHAR(255),
    total_quantity DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_harvest PRIMARY KEY (id)
);

CREATE TABLE harvest_detail
(
    id         UUID             NOT NULL,
    tree_id    UUID             NOT NULL,
    harvest_id UUID             NOT NULL,
    quantity   DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_harvestdetail PRIMARY KEY (id)
);

ALTER TABLE harvest_detail
    ADD CONSTRAINT FK_HARVESTDETAIL_ON_HARVEST FOREIGN KEY (harvest_id) REFERENCES harvest (id);

ALTER TABLE harvest_detail
    ADD CONSTRAINT FK_HARVESTDETAIL_ON_TREE FOREIGN KEY (tree_id) REFERENCES tree (id);

ALTER TABLE harvest
    ADD CONSTRAINT FK_HARVEST_ON_FIELD FOREIGN KEY (field_id) REFERENCES field (id);