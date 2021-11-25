-- before inserting anythin get an id for the order
-- use that value for order_id in the next statements
SELECT NEXTVAL('order_pk_seq');
-- insert general order information
-- order date will be set to the 
-- current date. set total to 0
INSERT INTO orders(order_id,restaurant_id, user_name, order_status, order_date, total)
VALUES (
    2222,  -- value from select statment above
    1,
    'moritz',
    1,
    current_date,
    0.0
);
-- insert all items for the order with 
-- current price
INSERT INTO orders_products VALUES (
    1,
    1,
    2,
    (SELECT price FROM products
    WHERE product_id = 1)
);
INSERT INTO orders_products VALUES (
    1,
    2,
    3,
    (SELECT price FROM products
    WHERE product_id = 2222)
);
-- This will calculate and set the orders total
UPDATE orders 
SET total = (
    SELECT sum(amount * product_price) 
    FROM orders_products
    WHERE order_id = 2222
)
WHERE order_id = 1;