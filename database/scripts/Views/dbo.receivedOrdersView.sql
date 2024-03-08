CREATE OR ALTER VIEW DeliveryDetailsView AS
SELECT
    a.street_address,
    a.suburb,
    a.city,
    c.alias AS customer_alias,
    cp.phone_number AS contactNumber,
    o.id AS Order_Id,
    COUNT(ol.id) AS NumberOfItems
FROM
    Addresses a
INNER JOIN
    Orders o ON o.address_id = a.id
INNER JOIN
    Customers c ON o.customer_id = c.id
INNER JOIN
    CustomerPhoneNumbers cp ON cp.customer_id = c.id
INNER JOIN
    OrderLines ol ON o.id = ol.order_id
GROUP BY
    a.street_address,
    a.suburb,
    a.city,
    c.alias,
    cp.phone_number,
    o.id;