-- insert general order information
-- order date will be set to the 
-- current date. set total to 0
INSERT INTO orders VALUES (
    'order111',
    'abcdef',
    'moritz',
    1,
    current_date,
    0.0
);
-- insert all items for the order with 
-- current price
INSERT INTO orders_products VALUES (
    'order111',
    'prod11234',
    2,
    (SELECT price FROM products
    WHERE product_id = 'prod11234')
);
INSERT INTO orders_products VALUES (
    'order111',
    'prod11235',
    1,
    (SELECT price FROM products
    WHERE product_id = 'prod11235')
);
-- This will calculate and set the orders total
UPDATE orders 
SET total = (
    SELECT sum(amount * product_price) 
    FROM orders_products
    WHERE order_id = 'order111'
)
WHERE order_id = 'order111';