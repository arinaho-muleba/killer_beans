CREATCREATE OR ALTER VIEW BeanPricesView AS
SELECT
    b.name AS bean_name,
    p.price AS bean_price,
	b.id
FROM
    Beans b
INNER JOIN
    Prices p ON b.id = p.bean_id;