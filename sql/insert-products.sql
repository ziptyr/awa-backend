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