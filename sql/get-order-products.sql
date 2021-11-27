SELECT product_name, op.amount, op.product_price, op.amount * op.product_price as total_price FROM orders_products op
JOIN products p on (op.product_id = p.product_id)
where op.order_id = 1;

--       name      | amount | product_price | total_price
-- ----------------+--------+---------------+-------------
--  Cheeseburger   |      2 |          2.50 |        5.00
--  Haloumi Burger |      1 |          3.50 |        3.50

select * from orders_products;
select * from products;
select * from orders;