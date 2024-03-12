CREATE OR REPLACE VIEW "track_orders_view" AS
SELECT
    o.id AS order_id,
    o.customer_id,
    o.date_time,
    o.address_id,
    s.status AS order_status,
    SUM(p.price * ol.quantity) AS total_price
FROM
    "orders" o
INNER JOIN
    "statuses" s ON o.status_id = s.id
INNER JOIN
    "order_lines" ol ON o.id = ol.order_id
INNER JOIN
    "prices" p ON ol.bean_id = p.bean_id
GROUP BY
    o.id, o.customer_id, o.date_time, o.address_id, s.status;
