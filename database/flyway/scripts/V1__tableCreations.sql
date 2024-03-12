CREATE TABLE "beans"(
    "id" INT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "time_to_kill" INT NOT NULL,
    "quantity" INT NOT NULL
);
ALTER TABLE
    "beans" ADD CONSTRAINT "beans_id_primary" PRIMARY KEY("id");

CREATE TABLE "prices"(
    "id" INT NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    "start_date" DATE NOT NULL,
    "end_date" DATE NULL,
    "bean_id" INT NOT NULL
);
ALTER TABLE
    "prices" ADD CONSTRAINT "prices_id_primary" PRIMARY KEY("id");

CREATE TABLE "agents"(
    "id" INT NOT NULL,
    "alias" VARCHAR(255) NOT NULL,
    "firstname" VARCHAR(255) NOT NULL,
    "lastname" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "agents" ADD CONSTRAINT "agents_id_primary" PRIMARY KEY("id");

CREATE TABLE "addresses"(
    "id" INT NOT NULL,
    "street_address" VARCHAR(255) NOT NULL,
    "suburb" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "addresses" ADD CONSTRAINT "addresses_id_primary" PRIMARY KEY("id");

CREATE TABLE "customers"(
    "id" INT NOT NULL,
    "alias" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "customers" ADD CONSTRAINT "customers_id_primary" PRIMARY KEY("id");

CREATE TABLE "agent_phone_numbers"(
    "id" INT NOT NULL,
    "agent_id" INT NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "agent_phone_numbers" ADD CONSTRAINT "agentphonenumbers_id_primary" PRIMARY KEY("id");

CREATE TABLE "statuses"(
    "id" INT NOT NULL,
    "status" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "statuses" ADD CONSTRAINT "statuses_id_primary" PRIMARY KEY("id");

CREATE TABLE "order_lines"(
    "id" INT NOT NULL,
    "order_id" INT NOT NULL,
    "bean_id" INT NOT NULL,
    "quantity" INT NOT NULL
);
ALTER TABLE
    "order_lines" ADD CONSTRAINT "orderlines_id_primary" PRIMARY KEY("id");

CREATE TABLE "orders"(
    "id" INT NOT NULL,
    "customer_id" INT NOT NULL,
    "date_time" DATETIME NOT NULL,
    "address_id" INT NOT NULL,
    "status_id" INT NOT NULL,
    "agent_id" INT NULL
);
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_id_primary" PRIMARY KEY("id");

CREATE TABLE "customer_phone_numbers"(
    "id" INT NOT NULL,
    "customer_id" INT NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "customer_phone_numbers" ADD CONSTRAINT "customerphonenumbers_id_primary" PRIMARY KEY("id");

ALTER TABLE
    "orders" ADD CONSTRAINT "orders_status_id_foreign" FOREIGN KEY("status_id") REFERENCES "statuses"("id");
ALTER TABLE
    "order_lines" ADD CONSTRAINT "orderlines_bean_id_foreign" FOREIGN KEY("bean_id") REFERENCES "beans"("id");
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_customer_id_foreign" FOREIGN KEY("customer_id") REFERENCES "customers"("id");
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_agent_id_foreign" FOREIGN KEY("agent_id") REFERENCES "agents"("id");
ALTER TABLE
    "order_lines" ADD CONSTRAINT "orderlines_order_id_foreign" FOREIGN KEY("order_id") REFERENCES "orders"("id");
ALTER TABLE
    "prices" ADD CONSTRAINT "prices_bean_id_foreign" FOREIGN KEY("bean_id") REFERENCES "beans"("id");
ALTER TABLE
    "customer_phone_numbers" ADD CONSTRAINT "customerphonenumbers_customer_id_foreign" FOREIGN KEY("customer_id") REFERENCES "customers"("id");
ALTER TABLE
    "agent_phone_numbers" ADD CONSTRAINT "agentphonenumbers_agent_id_foreign" FOREIGN KEY("agent_id") REFERENCES "agents"("id");
ALTER TABLE
    "orders" ADD CONSTRAINT "orders_address_id_foreign" FOREIGN KEY("address_id") REFERENCES "addresses"("id");
