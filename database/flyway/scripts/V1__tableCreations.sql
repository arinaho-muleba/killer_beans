CREATE TABLE "beans" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "time_to_kill" INT NOT NULL,
    "quantity" INT NOT NULL
);

CREATE TABLE "prices" (
    "id" SERIAL PRIMARY KEY,
    "price" DECIMAL(8, 2) NOT NULL,
    "start_date" DATE NOT NULL,
    "end_date" DATE NULL,
    "bean_id" INT NOT NULL
);

CREATE TABLE "agents" (
    "id" SERIAL PRIMARY KEY,
    "alias" VARCHAR(255) NOT NULL,
    "firstname" VARCHAR(255) NOT NULL,
    "lastname" VARCHAR(255) NOT NULL
);

CREATE TABLE "addresses" (
    "id" SERIAL PRIMARY KEY,
    "street_address" VARCHAR(255) NOT NULL,
    "suburb" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL
);

CREATE TABLE "customers" (
    "id" SERIAL PRIMARY KEY,
    "alias" VARCHAR(255) NOT NULL
);

CREATE TABLE "agent_phone_numbers" (
    "id" SERIAL PRIMARY KEY,
    "agent_id" INT NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL
);

CREATE TABLE "statuses" (
    "id" SERIAL PRIMARY KEY,
    "status" VARCHAR(255) NOT NULL
);

CREATE TABLE "order_lines" (
    "id" SERIAL PRIMARY KEY,
    "order_id" INT NOT NULL,
    "bean_id" INT NOT NULL,
    "quantity" INT NOT NULL
);

CREATE TABLE "orders" (
    "id" SERIAL PRIMARY KEY,
    "customer_id" INT NOT NULL,
    "date_time" TIMESTAMP NOT NULL,
    "address_id" INT NOT NULL,
    "status_id" INT NOT NULL,
    "agent_id" INT NULL
);

CREATE TABLE "customer_phone_numbers" (
    "id" SERIAL PRIMARY KEY,
    "customer_id" INT NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL
);
