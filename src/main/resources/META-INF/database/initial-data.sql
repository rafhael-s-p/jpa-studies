insert into tab_product (id, name, price, description) values (1, 'Kindle', 499.0, 'Meet the new Kindle, now with adjustable built-in lighting, which allows you to read indoors or outdoors, at any time of the day.');
insert into tab_product (id, name, price, description) values (3, 'GoPro Hero 7 Camera', 1400.0, '2x better performance.');

insert into tab_client (id, name) values (1, 'Jenni Jacklyn');
insert into tab_client (id, name) values (2, 'Georgie Esther');

insert into tab_order (id, client_id, order_date, total, status) values (1, 1, sysdate(), 200.0, 'WAITING');

insert into tab_order_item (id, order_id, product_id, product_price, amount) values (1, 1, 1, 5.0, 2);

insert into tab_category (id, name) values (1, 'Electronics');