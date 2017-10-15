/*日志*/
create table log(
	log_id varchar(32) not null,
	log_teac varchar(30) not null,
	log_time varchar(19),
	log_oper varchar(20),
	log_uri  varchar(100)
);
alter table log
	add constraint log_pk primary key(log_id);