CREATE TABLE "Beans"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "time_to_kill" INT NOT NULL,
    "quantity" INT NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "Prices"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    "start_date" DATE NOT NULL,
    "end_date" DATE NULL,
    "bean_id" INT NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "Agents"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "alias" VARCHAR(255) NOT NULL,
    "firstname" VARCHAR(255) NOT NULL,
    "lastname" VARCHAR(255) NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "Addresses"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "street_address" VARCHAR(255) NOT NULL,
    "suburb" VARCHAR(255) NOT NULL,
    "city" VARCHAR(255) NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "Customers"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "alias" VARCHAR(255) NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "AgentPhoneNumbers"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "agent_id" INT NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "Statuses"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "status" VARCHAR(255) NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "OrderLines"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "order_id" INT NOT NULL,
    "bean_id" INT NOT NULL,
    "quantity" INT NOT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "Orders"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "customer_id" INT NOT NULL,
    "date_time" DATETIME NOT NULL,
    "address_id" INT NOT NULL,
    "status_id" INT NOT NULL,
    "agent_id" INT NULL,
    PRIMARY KEY("id")
);

CREATE TABLE "CustomerPhoneNumbers"(
    "id" INT IDENTITY(1,1) NOT NULL,
    "customer_id" INT NOT NULL,
    "phone_number" VARCHAR(255) NOT NULL,
    PRIMARY KEY("id")
);