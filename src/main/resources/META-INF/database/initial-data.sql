insert into tab_product (id, name, price, created_at, description) values (1, 'Kindle', 499.0, date_sub(sysdate(), interval 1 day), 'Meet the new Kindle, now with adjustable built-in lighting, which allows you to read indoors or outdoors, at any time of the day.');
insert into tab_product (id, name, price, created_at, description) values (3, 'GoPro Hero 7 Camera', 1400.0, date_sub(sysdate(), interval 1 day), '2x better performance.');

insert into tab_client (id, name, ssn) values (1, 'Jenni Jacklyn', "051781594");
insert into tab_client (id, name, ssn) values (2, 'Georgie Esther', "040468979");

insert into tab_client_detail (client_id, gender, birthday) values (1, 'FEMALE', date_sub(sysdate(), interval 31 year));
insert into tab_client_detail (client_id, gender, birthday) values (2, 'FEMALE', date_sub(sysdate(), interval 31 year));

insert into tab_order (id, client_id, created_at, total, status) values (1, 1, sysdate(), 998.0, 'WAITING');
insert into tab_order (id, client_id, created_at, total, status) values (2, 1, sysdate(), 499.0, 'WAITING');

insert into tab_order_item (order_id, product_id, product_price, amount) values (1, 1, 499, 2);
insert into tab_order_item (order_id, product_id, product_price, amount) values (2, 1, 499, 1);

insert into tab_payment (order_id, status, payment_type, card_number, bar_code) values (2, 'PROCESSING', 'card', '5123716344050151', null);

insert into tab_category (id, name) values (1, 'Electronics');