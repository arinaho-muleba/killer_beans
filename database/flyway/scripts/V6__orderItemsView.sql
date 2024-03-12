CREATE OR REPLACE VIEW "order_items_view" AS
SELECT
    ol.order_id,
    ol.quantity,
    b.name AS bean_name,
    COUNT(ol.id) AS item_count
FROM
    "order_lines" ol
INNER JOIN
    "beans" b ON ol.bean_id = b.id
GROUP BY
    ol.order_id,
    ol.quantity,
    b.name;
