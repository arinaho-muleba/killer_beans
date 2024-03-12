CREATE OR REPLACE VIEW "delivery_details_view" AS
SELECT
    a.street_address,
    a.suburb,
    a.city,
    c.alias AS customer_alias,
    cp.phone_number AS contact_number,
    o.id AS order_id,
    COUNT(ol.id) AS number_of_items
FROM
    "addresses" a
INNER JOIN
    "orders" o ON o.address_id = a.id
INNER JOIN
    "customers" c ON o.customer_id = c.id
INNER JOIN
    "customer_phone_numbers" cp ON cp.customer_id = c.id
INNER JOIN
    "order_lines" ol ON o.id = ol.order_id
GROUP BY
    a.street_address,
    a.suburb,
    a.city,
    c.alias,
    cp.phone_number,
    o.id;
