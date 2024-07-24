create database blooddonate;
use blooddonate;

create table bloodreg(name varchar(50) not null, password varchar(50) not null, blood_group varchar(10) not null, phone_number varchar(15) not null, place varchar(50) not null);
select * from bloodreg;

create table admin(name varchar(50) not null, password varchar(50) not null);
insert into admin values("Admin", "admin");
select * from admin;


delete from bloodreg where blood_group = "A+";

truncate table bloodreg;

drop table bloodreg;