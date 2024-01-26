create or replace function average_above_revenue(average_value double precision) integer as $$ begin return (case (average_value > (select avg(total) from tab_order)) when true then 1 else 0 end); end; $$ language plpgsql;


create table tab_product_store (id integer not null, name varchar(100), price decimal(19, 2), created_at timestamp(6), updated_at timestamp(6), description varchar(500), product_photo bytea, primary key (id)) engine=InnoDB;
create table tab_product_ecm (prd_id integer not null, prd_name varchar(100), prd_price decimal(19, 2), prd_created_at timestamp(6), prd_updated_at timestamp(6), prd_description varchar(500), prd_product_photo bytea, primary key (prd_id)) engine=InnoDB;
create table tab_product_erp (id integer not null, name varchar(100), price decimal(19, 2), description varchar(500), primary key (id)) engine=InnoDB;
create table tab_category_ecm (cat_id integer not null, cat_name varchar(100), category_father_id integer, primary key (cat_id)) engine=InnoDB;

create or replace function find_product_name(in product_id int, out product_name varchar(255)) language plpgsql as $$ begin select name into product_name from tab_product where id = product_id; end; $$
create or replace function clients_who_bought_above_average(in sell_year integer) returns table(id int, version int, name varchar, ssn varchar, gender varchar, birthday timestamp) as $$ begin return query select cli.id, cli.version, cli.name, cli.ssn, cli_d.gender, cli_d.birthday::timestamp without time zone from tab_client cli join tab_client_detail cli_d on cli_d.client_id = cli.id join tab_order ord on ord.client_id = cli.id where ord.status = 'PAID' and extract(year from ord.created_at) = sell_year group by ord.client_id, cli.id, cli_d.client_id having sum(ord.total) >= (select avg(total_by_client.total_sum) from (select sum(ord2.total) total_sum from tab_order ord2 where ord2.status = 'PAID' and extract(year from ord2.created_at) = sell_year group by ord2.client_id) as total_by_client); end; $$ language plpgsql;
create or replace function adjust_price_product(in product_id int, in percentage_adjustment double precision) returns numeric as $$ declare product_price numeric; adjusted_price numeric; begin select price from tab_product where id = product_id into product_price; adjusted_price := (product_price + (product_price * percentage_adjustment)); update tab_product set price = adjusted_price where id = product_id; return adjusted_price; end; $$ language plpgsql;

create view clients_who_bought_above_average_view as select cli.*, cli_d.* from tab_client cli join tab_client_detail cli_d on cli_d.client_id = cli.id join tab_order ord on ord.client_id = cli.id where ord.status = 'PAID' and extract(year from ord.created_at) = extract(year from current_date) group by ord.client_id, cli.id, cli_d.client_id having sum(ord.total) >= (select avg(total_by_client.total_sum) from (select sum(ord2.total) total_sum from tab_order ord2 where ord2.status = 'PAID' and extract(year from ord2.created_at) = extract(year from current_date) group by ord2.client_id) as total_by_client);

alter sequence hibernate_sequence restart with 50;
--create table tab_category (id integer not null, name varchar(100) not null, category_father_id integer, primary key (id)) engine=InnoDB;
--create table tab_client (id integer not null, name varchar(100) not null, ssn varchar(11) not null, primary key (id)) engine=InnoDB;
--create table tab_client_contact (client_id integer not null, description varchar(255), type varchar(255) not null, primary key (client_id, type)) engine=InnoDB;
--create table tab_client_detail (birthday date, gender varchar(255), client_id integer not null, primary key (client_id)) engine=InnoDB;
--create table tab_invoice (order_id integer not null, emission_date timestamp(6) not null, xml bytea not null, primary key (order_id)) engine=InnoDB;
--create table tab_order (id integer not null, confirmed_at timestamp(6), created_at timestamp(6) not null, city varchar(50), complement varchar(50), district varchar(50), number varchar(10), postal varchar(9), state varchar(20), street varchar(100), status varchar(30) not null, total decimal(18,2) not null, updated_at timestamp(6), client_id integer not null, primary key (id)) engine=InnoDB;
--create table tab_order_item (order_id integer not null, product_id integer not null, amount integer not null, product_price decimal(19,2), primary key (order_id, product_id)) engine=InnoDB;
--create table tab_payment (payment_type varchar(31) not null, order_id integer not null, status varchar(30) not null, bar_code varchar(100), card_number varchar(50), primary key (order_id)) engine=InnoDB;
--create table tab_product (id integer not null, created_at timestamp(6) not null, description varchar(500), name varchar(100) not null, price decimal(18,2), product_photo bytea, updated_at timestamp(6), primary key (id)) engine=InnoDB;
--create table tab_product_category (product_id integer not null, category_id integer not null) engine=InnoDB;
--create table tab_product_characteristic (product_id integer not null, name varchar(100) not null, value varchar(255)) engine=InnoDB;
--create table tab_product_tag (product_id integer not null, tag varchar(255)) engine=InnoDB;
--create table tab_warehouse (id integer not null, amount integer, product_id integer not null, primary key (id)) engine=InnoDB;

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