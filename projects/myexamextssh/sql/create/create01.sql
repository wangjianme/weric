/*测试*/
create table test(
	id varchar(32) primary key,
	name varchar(30)
);

/*院系设置*/
create table dept(
	dept_id varchar(32) not null,
	dept_name varchar(30),
	dept_parent varchar(32),
	dept_isleaf char(1),
	dept_desc varchar(50)
);
alter table dept
  add constraint dept_pk primary key(dept_id);
/*学制*/
create table edusys(
	edusys_id varchar(32) not null,
	edusys_name varchar(50),
	edusys_month int,
	edusys_desc varchar(100)
);
alter table edusys
 	add constraint edysys_pk primary key(edusys_id);
/*专业设置*/
create table major(
	major_id varchar(32) not null,
	major_name varchar(30),
	major_dept varchar(32),
	major_edusys varchar(32),
	major_desc varchar(200),
	major_inuse char(1)
);
alter table major 
	add constraint major_pk primary key(major_id);
alter table major
    add constraint major_fk foreign key(major_dept) references dept(dept_id);
alter table major
	add constraint major_fkedusys foreign key(major_edusys) references edusys(edusys_id);
/*题型*/
create table cate(
	cate_id varchar(32) not null,
	cate_name varchar(30),
	cate_order int,
	cate_key varchar(32),
	cate_desc varchar(50)
);
alter table cate
	add constraint cate_pk primary key(cate_id);
/*课程设置*/
create table cour(
	cour_id varchar(32) not null,
	cour_name varchar(30),
	cour_desc varchar(200),
	cour_hour int,
	cour_ispubs char(1),		/*是否是公共课*/
	cour_inuse char(1)
);
alter table cour
   add constraint cour_pk primary key(cour_id);
/*课程所属于的专业，有的课程属于1或两个专业，有的课程为公开课*/
create table courmajor(
	cm_cour varchar(32) not null,
    cm_major varchar(32) not null
);
alter table courmajor
	add constraint cm_pk primary key(cm_cour,cm_major);
alter table courmajor
    add constraint cm_fkcour foreign key(cm_cour) references cour(cour_id);
alter table courmajor
    add constraint cm_fkmajor foreign key(cm_major) references major(major_id);

/*#######################班级的一些信息##########***/
/*班级定义*/
create table cls(
	cls_id varchar(32) not null,
	cls_name varchar(30),
	cls_head varchar(32),
	cls_major varchar(32),
	cls_btime varchar(19),
	cls_etime varchar(19),
	cls_state char(1)
);
alter table cls
	add constraint cls_pk primary key(cls_id);
alter table cls
	add constraint cls_fk1 foreign key(cls_major) references major(major_id);
/*学生信息表*/
create table stud(
	stud_id varchar(32) not null,
	stud_name varchar(20),
	stud_pwd varchar(32),
	stud_sex char(1),
	stud_sid varchar(18),	/*身份证号*/
	stud_birth varchar(19),
	stud_no varchar(50),/*学号*/
	stud_cls varchar(32),	/*班级ID*/
	stud_rtime varchar(19),
	stud_pic varchar(50),
	stud_addr varchar(100),
	stud_tel  varchar(50),
	stud_father varchar(20),
	stud_mother varchar(20),
	stud_qq     varchar(50),
	stud_email  varchar(50),
	stud_desc   varchar(200),
	stud_ques  varchar(100),
	stud_answer varchar(32),
	stud_state char(1) default '1'
);
alter table stud
	add constraint stud_pk primary key(stud_id);
alter table stud
	add constraint stud_fk1 foreign key(stud_cls) references cls(cls_id);
/*教师表*/
create table teac(
	teac_id varchar(32) not null,
	teac_name varchar(20) not null,
	teac_pwd  varchar(32) not null,
	teac_sex char(1),
	teac_time varchar(19),
	teac_state char(1) default '1',
	teac_dept varchar(32),
	teac_email varchar(50),
	teac_tel   varchar(50),
	teac_ques varchar(100),
	teac_answer varchar(32)
);
alter table teac
	add constraint teac_pk primary key(teac_id);
alter table teac
	add constraint teac_fkdept foreign key(teac_dept) references dept(dept_id);
/*用户组，即角色管理*/
create table role(
	role_id varchar(32) not null,
	role_name varchar(50),
	role_candel char(1) default '1',
	role_desc varchar(100)
);
alter table role 
 	add constraint role_pk primary key(role_id);

/*教师用户组对照*/
create table teacrole(
	tc_teac varchar(32) not null,
	tc_role varchar(32) not null
);
alter table teacrole
   add constraint tc_pk primary key(tc_teac,tc_role);
alter table teacrole
   add constraint tc_tfk foreign key(tc_teac) references teac(teac_id);
alter table teacrole
   add constraint tc_rfk foreign key(tc_role) references role(role_id);

/*功能菜单定义*/
create table menu(
	menu_id varchar(32) not null,
	menu_text varchar(40),
	menu_parent varchar(32),
	menu_leaf  char(1),
	menu_url   varchar(100),
	menu_target varchar(20),
	menu_issys char(1) default '0',
	menu_order int default 50
);
alter table menu
	add constraint menu_pk primary key(menu_id);

/*角色菜单对应表*/
create table func(
	func_role varchar(32) not null,
	func_menu varchar(32) not null
);
alter table func
  add constraint func_pk primary key(func_role,func_menu);
alter table func
  add constraint func_rfk foreign key(func_role) references role(role_id);
alter table func
  add constraint func_mfk foreign key(func_menu) references menu(menu_id);