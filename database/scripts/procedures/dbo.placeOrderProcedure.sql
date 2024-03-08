CREATE OR ALTER PROCEDURE placeOrderProcedure
    @street_address VARCHAR(255),
    @suburb VARCHAR(255),
    @city VARCHAR(255),
    @customer_id INT,
    @items VARCHAR(MAX),
    @quantities VARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insert into Addresses table
        INSERT INTO Addresses (street_address, suburb, city)
        VALUES (@street_address, @suburb, @city);

        -- Get the recent addressId
        DECLARE @addressId INT;
        SET @addressId = SCOPE_IDENTITY();

        -- Create an order in the Orders table
        DECLARE @dateTime DATETIME;
        SET @dateTime = GETDATE();

        INSERT INTO Orders (customer_id, date_time, address_id, status_id, agent_id)
        VALUES (@customer_id, @dateTime, @addressId, 1, NULL);

        -- Get the recent orderId
        DECLARE @orderId INT;
        SET @orderId = SCOPE_IDENTITY();

        -- Populate OrderLines
        DECLARE @itemId INT;
        DECLARE @quantity INT;
        DECLARE @itemList VARCHAR(MAX);
        DECLARE @quantityList VARCHAR(MAX);
        SET @itemList = @items;
        SET @quantityList = @quantities;

        WHILE LEN(@itemList) > 0 AND LEN(@quantityList) > 0
        BEGIN
            SET @itemId = NULL;
            SET @quantity = NULL;

            -- Parse item ID
            IF CHARINDEX(',', @itemList) > 0
            BEGIN
                SET @itemId = CAST(LEFT(@itemList, CHARINDEX(',', @itemList) - 1) AS INT);
                SET @itemList = RIGHT(@itemList, LEN(@itemList) - CHARINDEX(',', @itemList));
            END
            ELSE
            BEGIN
                SET @itemId = CAST(@itemList AS INT);
                SET @itemList = '';
            END

            -- Parse quantity
            IF CHARINDEX(',', @quantityList) > 0
            BEGIN
                SET @quantity = CAST(LEFT(@quantityList, CHARINDEX(',', @quantityList) - 1) AS INT);
                SET @quantityList = RIGHT(@quantityList, LEN(@quantityList) - CHARINDEX(',', @quantityList));
            END
            ELSE
            BEGIN
                SET @quantity = CAST(@quantityList AS INT);
                SET @quantityList = '';
            END

            -- Insert into OrderLines table
            INSERT INTO OrderLines (order_id, bean_id, quantity)
            VALUES (@orderId, @itemId, @quantity);

            -- Update the Beans table to reduce stockQuantity
            UPDATE Beans
            SET quantity = quantity - @quantity
            WHERE id = @itemId;
        END;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        THROW;
    END CATCH;
END;
