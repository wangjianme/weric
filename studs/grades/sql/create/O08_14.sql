/*专业设置表*/
create table major(
	major_id varchar(32) primary key,
	major_name varchar(50),
	major_desc varchar(500),/*专业描述*/
	major_deptid varchar(32),/*引用学院id*/
	constraint major_fk1 foreign key(major_deptid) references departments(dept_id)
);