ALTER TABLE "orders"
    ADD CONSTRAINT "orders_status_id_foreign" FOREIGN KEY ("status_id") REFERENCES "statuses"("id");

ALTER TABLE "orderLines"
    ADD CONSTRAINT "orderlines_bean_id_foreign" FOREIGN KEY ("bean_id") REFERENCES "beans"("id");

ALTER TABLE "orders"
    ADD CONSTRAINT "orders_customer_id_foreign" FOREIGN KEY ("customer_id") REFERENCES "customers"("id");

ALTER TABLE "orders"
    ADD CONSTRAINT "orders_agent_id_foreign" FOREIGN KEY ("agent_id") REFERENCES "agents"("id");

ALTER TABLE "orderLines"
    ADD CONSTRAINT "orderlines_order_id_foreign" FOREIGN KEY ("order_id") REFERENCES "orders"("id");

ALTER TABLE "prices"
    ADD CONSTRAINT "prices_bean_id_foreign" FOREIGN KEY ("bean_id") REFERENCES "beans"("id");

ALTER TABLE "customerPhoneNumbers"
    ADD CONSTRAINT "customerphonenumbers_customer_id_foreign" FOREIGN KEY ("customer_id") REFERENCES "customers"("id");

ALTER TABLE "agentPhoneNumbers"
    ADD CONSTRAINT "agentphonenumbers_agent_id_foreign" FOREIGN KEY ("agent_id") REFERENCES "agents"("id");

ALTER TABLE "orders"
    ADD CONSTRAINT "orders_address_id_foreign" FOREIGN KEY ("address_id") REFERENCES "addresses"("id");
