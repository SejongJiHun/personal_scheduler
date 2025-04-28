set global local_infile=1;

grant file on *.* to 'root'@'localhost';
flush privileges;

create database if not exists personal_scheduler_db;
use personal_scheduler_db;

create table if not exists user(
	id int auto_increment primary key,
    email varchar(50),
    password varchar(100),
    nickanem varchar(10),
    role varchar(10)

);