
create table users (
    user_name VARCHAR(50) NOT NULL PRIMARY KEY,
    address VARCHAR(50) NOT NULL,
    manager BOOLEAN NOT NULL,
    password_hash VARCHAR(50) NOT NULL
);
create table restaurants (
    restaurant_id INT NOT NULL PRIMARY KEY,
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
    product_id INT NOT NULL PRIMARY KEY,
    restaurant_id INT REFERENCES restaurants (restaurant_id),
    product_name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    price NUMERIC NOT NULL,
    image VARCHAR(100),
    categories VARCHAR(100)
);
create table orders (
    order_id INT NOT NULL PRIMARY KEY,
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