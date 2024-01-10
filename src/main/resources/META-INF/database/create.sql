create function average_above_revenue(average_value double) returns boolean reads sql data return average_value > (select avg(total) from tab_order);

create table tab_product_store (id integer not null auto_increment, name varchar(100), price decimal(19, 2), created_at datetime(6), updated_at datetime(6), description longtext, product_photo longblob, primary key (id)) engine=InnoDB;
create table tab_product_ecm (prd_id integer not null auto_increment, prd_name varchar(100), prd_price decimal(19, 2), prd_created_at datetime(6), prd_updated_at datetime(6), prd_description longtext, prd_product_photo longblob, primary key (prd_id)) engine=InnoDB;
create table tab_product_erp (id integer not null auto_increment, name varchar(100), price decimal(19, 2), description longtext, primary key (id)) engine=InnoDB;
create table tab_category_ecm (cat_id integer not null auto_increment, cat_name varchar(100), category_father_id integer, primary key (cat_id)) engine=InnoDB;

create procedure find_product_name(in product_id int, out product_name varchar(255)) begin select name into product_name from tab_product where id = product_id; end
create procedure clients_who_bought_above_average(in sell_year integer) begin select cli.*, cli_d.* from tab_client cli join tab_client_detail cli_d on cli_d.client_id = cli.id join tab_order ord on ord.client_id = cli.id where ord.status = 'PAID' and year(ord.created_at) = sell_year group by ord.client_id having sum(ord.total) >= (select avg(total_by_client.total_sum) from (select sum(ord2.total) total_sum from tab_order ord2 where ord2.status = 'PAID' and year(ord2.created_at) = sell_year group by ord2.client_id) as total_by_client); end
create procedure adjust_price_product(in product_id int, in percentage_adjustment double, out adjusted_price double) begin declare product_price double; select price into product_price from tab_product where id = product_id; set adjusted_price = product_price + (product_price * percentage_adjustment); update tab_product set price = adjusted_price where id = product_id; end

create view clients_who_bought_above_average_view as select cli.*, cli_d.* from tab_client cli join tab_client_detail cli_d on cli_d.client_id = cli.id join tab_order ord on ord.client_id = cli.id where ord.status = 'PAID' and year(ord.created_at) = year(current_date) group by ord.client_id having sum(ord.total) >= (select avg(total_by_client.total_sum) from (select sum(ord2.total) total_sum from tab_order ord2 where ord2.status = 'PAID' and year(ord2.created_at) = year(current_date) group by ord2.client_id) as total_by_client);

--create table tab_category (id integer not null auto_increment, name varchar(100) not null, category_father_id integer, primary key (id)) engine=InnoDB;
--create table tab_client (id integer not null auto_increment, name varchar(100) not null, ssn varchar(11) not null, primary key (id)) engine=InnoDB;
--create table tab_client_contact (client_id integer not null, description varchar(255), type varchar(255) not null, primary key (client_id, type)) engine=InnoDB;
--create table tab_client_detail (birthday date, gender varchar(255), client_id integer not null, primary key (client_id)) engine=InnoDB;
--create table tab_invoice (order_id integer not null, emission_date datetime(6) not null, xml longblob not null, primary key (order_id)) engine=InnoDB;
--create table tab_order (id integer not null auto_increment, confirmed_at datetime(6), created_at datetime(6) not null, city varchar(50), complement varchar(50), district varchar(50), number varchar(10), postal varchar(9), state varchar(20), street varchar(100), status varchar(30) not null, total decimal(18,2) not null, updated_at datetime(6), client_id integer not null, primary key (id)) engine=InnoDB;
--create table tab_order_item (order_id integer not null, product_id integer not null, amount integer not null, product_price decimal(19,2), primary key (order_id, product_id)) engine=InnoDB;
--create table tab_payment (payment_type varchar(31) not null, order_id integer not null, status varchar(30) not null, bar_code varchar(100), card_number varchar(50), primary key (order_id)) engine=InnoDB;
--create table tab_product (id integer not null auto_increment, created_at datetime(6) not null, description longtext, name varchar(100) not null, price decimal(18,2), product_photo longblob, updated_at datetime(6), primary key (id)) engine=InnoDB;
--create table tab_product_category (product_id integer not null, category_id integer not null) engine=InnoDB;
--create table tab_product_characteristic (product_id integer not null, name varchar(100) not null, value varchar(255)) engine=InnoDB;
--create table tab_product_tag (product_id integer not null, tag varchar(255)) engine=InnoDB;
--create table tab_warehouse (id integer not null auto_increment, amount integer, product_id integer not null, primary key (id)) engine=InnoDB;

--create index idx_category_name on tab_category (name);
--create index idx_client_name on tab_client (name);
--create index idx_product_name on tab_product (name);

--alter table tab_category add constraint unq_name unique (name);
--alter table tab_client add constraint unq_ssn unique (ssn);
--alter table tab_product add constraint unq_name unique (name);
--alter table tab_category add constraint fk_category_categoryfather foreign key (category_father_id) references tab_category (id);
--alter table tab_client_contact add constraint fk_client_contact_client foreign key (client_id) references tab_client (id);
--alter table tab_client_detail add constraint fk_client_detail_client foreign key (client_id) references tab_client (id);
--alter table tab_invoice add constraint fk_invoice_order foreign key (order_id) references tab_order (id);
--alter table tab_order add constraint fk_order_client foreign key (client_id) references tab_client (id);
--alter table tab_order_item add constraint fk_order_item_order foreign key (order_id) references tab_order (id);
--alter table tab_order_item add constraint fk_order_item_product foreign key (product_id) references tab_product (id);
--alter table tab_payment add constraint fk_payment_order foreign key (order_id) references tab_order (id);
--alter table tab_product_category add constraint fk_product_category_category foreign key (category_id) references tab_category (id);
--alter table tab_product_category add constraint fk_product_category_product foreign key (product_id) references tab_product (id);
--alter table tab_product_characteristic add constraint fk_product_characteristic_product foreign key (product_id) references tab_product (id);
--alter table tab_product_tag add constraint fk_product_tag_product foreign key (product_id) references tab_product (id);
--alter table tab_warehouse add constraint fk_warehouse_product foreign key (product_id) references tab_product (id)