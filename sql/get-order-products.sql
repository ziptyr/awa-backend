SELECT p.name, op.amount, op.unit_price, op.amount * op.unit_price as total_price FROM orders_products op
JOIN products p on (op.product_id = p.product_id)
where op.order_id = 'order111';

--       name      | amount | product_price | total_price
-- ----------------+--------+---------------+-------------
--  Cheeseburger   |      2 |          2.50 |        5.00
--  Haloumi Burger |      1 |          3.50 |        3.50