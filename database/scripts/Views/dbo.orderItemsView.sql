CREATE OR REPLACE VIEW OrderItemsView AS
SELECT
    ol.order_id,
    ol.quantity,
    b.name AS bean_name,
    COUNT(ol.id) AS item_count
FROM
    OrderLines ol
INNER JOIN
    Beans b ON ol.bean_id = b.id
GROUP BY
    ol.order_id,
    ol.quantity,
    b.name;