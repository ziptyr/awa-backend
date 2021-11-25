-- Create sequences
create sequence restaurant_pk_seq;
create sequence product_pk_seq;
create sequence order_pk_seq;


-- Create tables
create table users (
    user_name VARCHAR(50) NOT NULL PRIMARY KEY,
    address VARCHAR(50) NOT NULL,
    manager BOOLEAN NOT NULL,
    password_hash VARCHAR(50) NOT NULL
);
create table restaurants (
    restaurant_id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('restaurant_pk_seq'),
    restaurant_name VARCHAR(50) NOT NULL,
    manager_name VARCHAR(20) REFERENCES users (user_name),
    address VARCHAR(50) NOT NULL,
    opens VARCHAR(5) NOT NULL,
    closes VARCHAR(5) NOT NULL,
    price_level SMALLINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    image VARCHAR(100) NOT NULL
);
create table products (
    product_id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('product_pk_seq'),
    restaurant_id INT REFERENCES restaurants (restaurant_id),
    product_name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    price NUMERIC NOT NULL,
    image VARCHAR(100),
    categories VARCHAR(100)
);
create table orders (
    order_id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('order_pk_seq'),
    restaurant_id INT REFERENCES restaurants (restaurant_id),
    user_name VARCHAR(50) REFERENCES users (user_name),
    order_status SMALLINT NOT NULL,
    order_date DATE NOT NULL,
    total NUMERIC NOT NULL
);
create table orders_products (
    order_id INT REFERENCES orders (order_id),
    product_id INT REFERENCES products (product_id),
    amount SMALLINT NOT NULL,
    product_price NUMERIC NOT NULL,
    PRIMARY KEY(order_id, product_id)
);


-- insert some data

insert into users values (
    'lucas',
    'Sargans',
    TRUE,
    'öawbfcjkh3öoi'
);
insert into users values (
    'moritz',
    'Zürich',
    FALSE,
    'öaih3bvii12m'
);

INSERT INTO restaurants (restaurant_name, manager_name, address, opens, closes, price_level, type, image) VALUES (
    'best burgers',
    'lucas',
    'sargans',
    '9:00',
    '22:00',
    2,
    'fast food',
    '/image/path'
);
INSERT INTO restaurants (restaurant_name, manager_name, address, opens, closes, price_level, type, image) VALUES (
    'even better burgers',
    'lucas',
    'sargans',
    '9:00',
    '22:00',
    1,
    'street food',
    '/image/path'
);
INSERT INTO products(restaurant_id, product_name, description, price, image, categories)
VALUES (
    (SELECT restaurant_id from restaurants WHERE restaurant_name = 'best burgers'),
    'Cheeseburger',
    'some cheese burger',
    2.50,
    '/image/path',
    'cheese, cheep'
);
INSERT INTO products(restaurant_id, product_name, description, price, image, categories)
VALUES (
    (SELECT restaurant_id from restaurants WHERE restaurant_name = 'best burgers'),
    'Haloumi Burger',
    'some vegi burger',
    3.50,
    '/image/path',
    'cheese, vegi'
);


INSERT INTO orders(restaurant_id, user_name, order_status, order_date, total)
VALUES (
    (SELECT restaurant_id from restaurants WHERE restaurant_name = 'best burgers'),
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
    WHERE product_id = 2)
);
-- This will calculate and set the orders total
UPDATE orders 
SET total = (
    SELECT sum(amount * product_price) 
    FROM orders_products
    WHERE order_id = 1
)
WHERE order_id = 1;