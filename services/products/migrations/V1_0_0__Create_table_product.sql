CREATE TABLE products.product
(
    id          SERIAL PRIMARY KEY,
    code        VARCHAR(5),
    name        VARCHAR(255),
    description TEXT,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted  BOOLEAN   DEFAULT FALSE
);

CREATE TABLE products.material
(
    id         SERIAL PRIMARY KEY,
    code       VARCHAR(5),
    name       VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN   DEFAULT FALSE
);

CREATE TABLE products.category
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN   DEFAULT FALSE
);

CREATE TABLE products.brand
(
    id         SERIAL PRIMARY KEY,
    code       VARCHAR(5),
    name       VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN   DEFAULT FALSE
);

CREATE TABLE products.color
(
    id         SERIAL PRIMARY KEY,
    code       VARCHAR(5),
    name       VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN   DEFAULT FALSE
);
CREATE TABLE products.size
(
    id         SERIAL PRIMARY KEY,
    code       VARCHAR(5),
    name       VARCHAR(255),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN   DEFAULT FALSE
);
CREATE TABLE products.product_detail
(
    id          SERIAL PRIMARY KEY,
    code        VARCHAR(5),
    name        VARCHAR(255),
    quantity    INTEGER,
    price       DOUBLE PRECISION,
    image       jsonb,
    description TEXT,
    material_id BIGINT,
    color_id    BIGINT,
    size_id     BIGINT,
    brand_id    BIGINT,
    category_id BIGINT,
    product_id  BIGINT,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted  BOOLEAN   DEFAULT FALSE

)