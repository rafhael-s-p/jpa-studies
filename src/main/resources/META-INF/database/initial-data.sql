insert into tab_product (id, version, name, price, created_at, active, description) values (1, 0, 'Kindle', 799.0, (now() - interval '1 day'), 'YES', 'Meet the new Kindle, now with adjustable built-in lighting, which allows you to read indoors or outdoors, at any time of the day.');
insert into tab_product (id, version, name, price, created_at, active, description) values (3, 0, 'GoPro Hero 7 Camera', 1500.0, (now() - interval '1 day'), 'YES', '2x better performance.');
insert into tab_product (id, version, name, price, created_at, active, description) values (4, 0, 'Canon 80D Camera', 3500.0, now(), 'YES', 'The best focus adjustment.');
insert into tab_product (id, version, name, price, created_at, active, description) values (5, 0, 'Lapel microphone', 80.0, now(), 'NO', 'Good sound capture.');

insert into tab_client (id, version, name, ssn) values (1, 0, 'Jenni Jacklyn', "051781594");
insert into tab_client (id, version, name, ssn) values (2, 0, 'Georgie Esther', "040468979");

insert into tab_client_detail (client_id, gender, birthday) values (1, 'FEMALE', (now() - interval '31 year'));
insert into tab_client_detail (client_id, gender, birthday) values (2, 'FEMALE', (now() - interval '31 year'));

insert into tab_order (id, version, client_id, created_at, total, status) values (1, 0, 1, (now() - interval '32 year'), 2398.0, 'WAITING');
insert into tab_order (id, version, client_id, created_at, total, status) values (2, 0, 1, (now() - interval '5 year'), 499.0, 'WAITING');
insert into tab_order (id, version, client_id, created_at, total, status) values (3, 0, 1, (now() - interval '4 year'), 3500.0, 'PAID');
insert into tab_order (id, version, client_id, created_at, total, status) values (4, 0, 2, (now() - interval '2 year'), 499.0, 'PAID');
insert into tab_order (id, version, client_id, created_at, total, status) values (5, 0, 1, (now() - interval '2 year'), 799.0, 'PAID');
insert into tab_order (id, version, client_id, created_at, total, status) values (6, 0, 2, now(), 799.0, 'WAITING');

insert into tab_order_item (order_id, product_id, product_price, amount) values (1, 1, 499, 2);
insert into tab_order_item (order_id, product_id, product_price, amount) values (1, 3, 1400, 1);
insert into tab_order_item (order_id, product_id, product_price, amount) values (2, 1, 499, 1);
insert into tab_order_item (order_id, product_id, product_price, amount) values (3, 4, 3500, 1);
insert into tab_order_item (order_id, product_id, product_price, amount) values (4, 1, 499, 1);
insert into tab_order_item (order_id, product_id, product_price, amount) values (5, 1, 799, 1);
insert into tab_order_item (order_id, product_id, product_price, amount) values (6, 1, 799, 1);

insert into tab_payment (order_id, version, status, payment_type, card_number, bar_code) values (1, 0, 'PROCESSING', 'card', '5123716344050151', null);
insert into tab_payment (order_id, version, status, payment_type, card_number, bar_code) values (2, 0, 'RECEIVED', 'card', '5123716344050152', null);
insert into tab_payment (order_id, version, status, payment_type, card_number, bar_code, due_date) values (3, 0, 'RECEIVED', 'slip', null, '5123716344050153', (now() - interval '2 year'));
insert into tab_payment (order_id, version, status, payment_type, card_number, bar_code) values (4, 0, 'PROCESSING', 'card', '5123716344050151', null);
insert into tab_payment (order_id, version, status, payment_type, card_number, bar_code, due_date) values (6, 0, 'PROCESSING', 'slip', null, '5123716344050154', (now() - interval '2 year'));

insert into tab_invoice (order_id, version, xml, emission_date) values (2, 0, '<xml><xml/>', now());

insert into tab_category (name, version) values ('Electronics', 0);
insert into tab_category (name, version) values ('Books', 0);
insert into tab_category (name, version) values ('Sports', 0);
insert into tab_category (name, version) values ('Soccer', 0);
insert into tab_category (name, version) values ('Swimming', 0);
insert into tab_category (name, version) values ('Laptops', 0);
insert into tab_category (name, version) values ('Smartphones', 0);
insert into tab_category (name, version) values ('Cameras', 0);

insert into tab_product_category (product_id, category_id) values (1, 2);
insert into tab_product_category (product_id, category_id) values (3, 8);
insert into tab_product_category (product_id, category_id) values (4, 8);

insert into tab_product_store (id, name, price, created_at, description) values (101, 'Kindle', 799.0, (now() - interval '1 day'), 'Meet the new Kindle, now with adjustable built-in lighting, which allows you to read indoors or outdoors, at any time of the day.');
insert into tab_product_store (id, name, price, created_at, description) values (103, 'GoPro Hero 7 Camera', 1500.0, (now() - interval '1 day'), '2x better performance.');
insert into tab_product_store (id, name, price, created_at, description) values (104, 'Canon 80D Camera', 3500.0, now(), 'The best focus adjustment.');
insert into tab_product_store (id, name, price, created_at, description) values (105, 'Lapel microphone', 80.0, now(), 'Good sound capture.');

insert into tab_product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (201, 'Kindle', 799.0, (now() - interval '1 day'), 'Meet the new Kindle, now with adjustable built-in lighting, which allows you to read indoors or outdoors, at any time of the day.');
insert into tab_product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (203, 'GoPro Hero 7 Camera', 1500.0, (now() - interval '1 day'), '2x better performance.');
insert into tab_product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (204, 'Canon 80D Camera', 3500.0, now(), 'The best focus adjustment.');
insert into tab_product_ecm (prd_id, prd_name, prd_price, prd_created_at, prd_description) values (205, 'Lapel microphone', 80.0, now(), 'Good sound capture.');

insert into tab_product_erp (id, name, price, description) values (301, 'Kindle', 799.0, 'Meet the new Kindle, now with adjustable built-in lighting, which allows you to read indoors or outdoors, at any time of the day.');
insert into tab_product_erp (id, name, price, description) values (303, 'GoPro Hero 7 Camera', 1500.0, '2x better performance.');
insert into tab_product_erp (id, name, price, description) values (304, 'Canon 80D Camera', 3500.0, 'The best focus adjustment.');
insert into tab_product_erp (id, name, price, description) values (305, 'Lapel microphone', 80.0, 'Good sound capture.');

insert into tab_category_ecm (cat_name) values ('Electronics');
insert into tab_category_ecm (cat_name) values ('Books');
insert into tab_category_ecm (cat_name) values ('Sports');
insert into tab_category_ecm (cat_name) values ('Soccer');
insert into tab_category_ecm (cat_name) values ('Swimming');
insert into tab_category_ecm (cat_name) values ('Laptops');
insert into tab_category_ecm (cat_name) values ('Smartphones');
insert into tab_category_ecm (cat_name) values ('Cameras');