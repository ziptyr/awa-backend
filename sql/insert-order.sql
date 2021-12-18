-- insert general order information
-- order date will be set to the 
-- current date. set total to 0
INSERT INTO orders(order_id,restaurant_id, user_name, order_status, order_date, total, delivery_address)
VALUES (
    2222,  -- value from select statment above
    1,
    'moritz',
    0,
    current_date,
    0.0,
    (SELECT address FROM users WHERE user_name = 'moritz')
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
WHERE order_id = 1;
-- If the delivery address is not the users home address
UPDATE orders 
SET delivery_address = 'Adlerweg 12, Zürich' 
WHERE order_id = 1;
