CREATE OR REPLACE PROCEDURE "placeOrderProcedure"(
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
    addressId INT;
    orderId INT;
    itemId INT;
    itemQuantity INT;
    itemList TEXT;
    quantityList TEXT;
    dateTime TIMESTAMP;
BEGIN
    -- Set NOCOUNT equivalent in PostgreSQL
    PERFORM NULL;

    -- Start transaction
    --BEGIN;
    
    -- Insert into Addresses table
    INSERT INTO "Addresses" ("street_address", "suburb", "city")
    VALUES (street_address, suburb, city)
    RETURNING "id" INTO addressId;

    -- Create an order in the Orders table
    SELECT NOW() INTO dateTime;

    INSERT INTO "Orders" ("customer_id", "date_time", "address_id", "status_id", "agent_id")
    VALUES (customer_id, dateTime, addressId, 1, NULL)
    RETURNING "id" INTO orderId;

    -- Populate OrderLines
    itemList := items;
    quantityList := quantities;

    WHILE LENGTH(itemList) > 0 AND LENGTH(quantityList) > 0 LOOP
        itemId := NULL;
        itemQuantity := NULL;

        -- Parse item ID
        IF POSITION(',' IN itemList) > 0 THEN
            itemId := CAST(SUBSTRING(itemList FROM 1 FOR POSITION(',' IN itemList) - 1) AS INT);
            itemList := SUBSTRING(itemList FROM POSITION(',' IN itemList) + 1);
        ELSE
            itemId := CAST(itemList AS INT);
            itemList := '';
        END IF;

        -- Parse quantity
        IF POSITION(',' IN quantityList) > 0 THEN
            itemQuantity := CAST(SUBSTRING(quantityList FROM 1 FOR POSITION(',' IN quantityList) - 1) AS INT);
            quantityList := SUBSTRING(quantityList FROM POSITION(',' IN quantityList) + 1);
        ELSE
            itemQuantity := CAST(quantityList AS INT);
            quantityList := '';
        END IF;

        -- Insert into OrderLines table
        INSERT INTO "OrderLines" ("order_id", "bean_id", "quantity")
        VALUES (orderId, itemId, itemQuantity);

        -- Update the Beans table to reduce quantity
        UPDATE "Beans"
        SET "quantity" = "quantity" - itemQuantity
        WHERE "id" = itemId;

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
