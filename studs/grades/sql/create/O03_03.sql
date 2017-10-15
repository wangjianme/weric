create table departments(

	dept_id varchar(32) primary key,
	dept_name varchar(50),
	dept_state varchar(10),
	dept_parent varchar(32),
	dept_isdept char(1),/*1是学院，0不是学院*/
	dept_addr varchar(50),
	dept_tel varchar(20),
	dept_iconcls varchar(50),/*学院图标*/
	dept_person int /*人员配置*/
);