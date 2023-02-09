drop table if exists bouquet cascade
drop table if exists flower cascade
drop table if exists flower_type cascade
drop sequence if exists bouquet_seq
drop sequence if exists flower_seq
drop sequence if exists flower_type_seq
create sequence bouquet_seq start with 1 increment by 50
create sequence flower_seq start with 1 increment by 50
create sequence flower_type_seq start with 1 increment by 50
create table bouquet (id bigint not null, name varchar(255), next_flower_pos integer not null, primary key (id))
create table flower (id bigint not null, length integer not null, level_of_freshness float(53) not null, name varchar(255), pos_in_bouquet integer not null, price_per_flower integer not null, bouquet_id bigint, type_id bigint, primary key (id))
create table flower_type (id bigint not null, name varchar(255), primary key (id))
alter table if exists bouquet add constraint UK_cpnu9qp7k6ejcw9uw7e8q75kc unique (name)
alter table if exists flower add constraint FKhh5mfkybke5c6cjv68lg6qyrn foreign key (bouquet_id) references bouquet
alter table if exists flower add constraint FKs5nnd3x9mqth5c4115levl742 foreign key (type_id) references flower_type

INSERT INTO flower_type VALUES ('1', 'Роза'),('2', 'Ромашка'),('3', 'Тюльпан')

INSERT INTO bouquet VALUES ('1', 'name', '4')

INSERT INTO flower VALUES ('10', '12', '0.4','Роза1', '3', '12', '1', '1')
INSERT INTO flower VALUES ('20', '13', '0.7','Роза2', '0', '13', null, '1')
INSERT INTO flower VALUES ('30', '14', '0.9','Ромашка', '2', '14', '1', '2')
INSERT INTO flower VALUES ('40', '15', '0.1','Тюльпан', '1', '15', '1', '3')

