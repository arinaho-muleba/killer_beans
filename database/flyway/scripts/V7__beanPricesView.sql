CREATE OR REPLACE VIEW "beanPricesView" AS
SELECT
    b."name" AS bean_name,
    p.price AS bean_price,
    b.id
FROM
    "beans" b
INNER JOIN
    "prices" p ON b.id = p.bean_id;
