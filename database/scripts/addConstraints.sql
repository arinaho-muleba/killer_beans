
ALTER TABLE "Orders"
    ADD CONSTRAINT "orders_status_id_foreign" FOREIGN KEY ("status_id") REFERENCES "Statuses"("id");

ALTER TABLE "OrderLines"
    ADD CONSTRAINT "orderlines_bean_id_foreign" FOREIGN KEY ("bean_id") REFERENCES "Beans"("id");

ALTER TABLE "Orders"
    ADD CONSTRAINT "orders_customer_id_foreign" FOREIGN KEY ("customer_id") REFERENCES "Customers"("id");

ALTER TABLE "Orders"
    ADD CONSTRAINT "orders_agent_id_foreign" FOREIGN KEY ("agent_id") REFERENCES "Agents"("id");

ALTER TABLE "OrderLines"
    ADD CONSTRAINT "orderlines_order_id_foreign" FOREIGN KEY ("order_id") REFERENCES "Orders"("id");

ALTER TABLE "Prices"
    ADD CONSTRAINT "prices_bean_id_foreign" FOREIGN KEY ("bean_id") REFERENCES "Beans"("id");

ALTER TABLE "CustomerPhoneNumbers"
    ADD CONSTRAINT "customerphonenumbers_customer_id_foreign" FOREIGN KEY ("customer_id") REFERENCES "Customers"("id");

ALTER TABLE "AgentPhoneNumbers"
    ADD CONSTRAINT "agentphonenumbers_agent_id_foreign" FOREIGN KEY ("agent_id") REFERENCES "Agents"("id");

ALTER TABLE "Orders"
    ADD CONSTRAINT "orders_address_id_foreign" FOREIGN KEY ("address_id") REFERENCES "Addresses"("id");
