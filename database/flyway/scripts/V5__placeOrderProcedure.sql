CREATE OR REPLACE PROCEDURE "place_order_procedure"(
    IN street_address VARCHAR(255),
    IN suburb VARCHAR(255),
    IN city VARCHAR(255),
    IN customer_id INT,
    IN items TEXT,
    IN quantities TEXT
)
LANGUAGE plpgsql
AS $$
DECLARE
    address_id INT;
    order_id INT;
    item_id INT;
    item_quantity INT;
    item_list TEXT;
    quantity_list TEXT;
    date_time TIMESTAMP;
BEGIN
    -- Set NOCOUNT equivalent in PostgreSQL
    PERFORM NULL;

    -- Start transaction
    --BEGIN;
    
    -- Insert into Addresses table
    INSERT INTO "addresses" ("street_address", "suburb", "city")
    VALUES (street_address, suburb, city)
    RETURNING "id" INTO address_id;

    -- Create an order in the Orders table
    SELECT NOW() INTO date_time;

    INSERT INTO "orders" ("customer_id", "date_time", "address_id", "status_id", "agent_id")
    VALUES (customer_id, date_time, address_id, 1, NULL)
    RETURNING "id" INTO order_id;

    -- Populate OrderLines
    item_list := items;
    quantity_list := quantities;

    WHILE LENGTH(item_list) > 0 AND LENGTH(quantity_list) > 0 LOOP
        item_id := NULL;
        item_quantity := NULL;

        -- Parse item ID
        IF POSITION(',' IN item_list) > 0 THEN
            item_id := CAST(SUBSTRING(item_list FROM 1 FOR POSITION(',' IN item_list) - 1) AS INT);
            item_list := SUBSTRING(item_list FROM POSITION(',' IN item_list) + 1);
        ELSE
            item_id := CAST(item_list AS INT);
            item_list := '';
        END IF;

        -- Parse quantity
        IF POSITION(',' IN quantity_list) > 0 THEN
            item_quantity := CAST(SUBSTRING(quantity_list FROM 1 FOR POSITION(',' IN quantity_list) - 1) AS INT);
            quantity_list := SUBSTRING(quantity_list FROM POSITION(',' IN quantity_list) + 1);
        ELSE
            item_quantity := CAST(quantity_list AS INT);
            quantity_list := '';
        END IF;

        -- Insert into OrderLines table
        INSERT INTO "order_lines" ("order_id", "bean_id", "quantity")
        VALUES (order_id, item_id, item_quantity);

        -- Update the Beans table to reduce quantity
        UPDATE "beans"
        SET "quantity" = "quantity" - item_quantity
        WHERE "id" = item_id;

    END LOOP;

    -- Commit transaction
    --COMMIT;
    
EXCEPTION
    WHEN OTHERS THEN
        -- Rollback transaction
        IF FOUND THEN
            --ROLLBACK;
        END IF;
        -- Raise the error
        RAISE;
END;
$$;
