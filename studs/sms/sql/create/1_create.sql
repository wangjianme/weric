/*创建用户的表管理员表*/
create table users(
	user_id varchar(32) primary key,
	user_name varchar(30) unique,
	user_pwd varchar(32),
	user_salt varchar(32),
	user_sex char(1) /*0:女,1:男*/
);


create table roles(
   role_id varchar(32) primary key,
   role_name varchar(30)
);

create table user_role(
    ur_userid varchar(32),
    ur_roleid varchar(32),
    constraint ur_pk primary key(ur_userid,ur_roleid),
    constraint ur_fk1 foreign key(ur_userid) references users(user_id),
    constraint ur_fk2 foreign key(ur_roleid) references roles(role_id)
);

create table menus(
	menu_id varchar(32) primary key,
	menu_text varchar(50),
	menu_res varchar(100),
	menu_state varchar(10),
	menu_parent varchar(32)
);

create table role_menu(
	rm_roleid varchar(32),
	rm_menuid varchar(32),
	 constraint rm_pk primary key(rm_menuid,rm_roleid),
    constraint rm_fk1 foreign key(rm_menuid) references menus(menu_id),
    constraint rm_fk2 foreign key(rm_roleid) references roles(role_id)
);

/*年级设置*/
create table grade(
	grade_id varchar(32) primary key,
	grade_name varchar(50),
	grade_desc varchar(200),/*说明*/
	grade_date varchar(19)/*录入的时间*/
); 
/*专业设置表*/
create table major(
	major_id varchar(32) primary key,
	major_name varchar(50),
	major_desc varchar(200)
);

/*年级专业关系表*/
create table grade_major(
	gm_gradeid varchar(32),
	gm_majorid varchar(32),
	constraint gm_pk primary key(gm_gradeid,gm_majorid),
	constraint gm_fk1 foreign key(gm_gradeid) references grade(grade_id),
	constraint gm_fk2 foreign key(gm_majorid) references major(major_id)
);

/*课程设置*/
create table course(
	course_id varchar(32) primary key,
	course_name varchar(50),
	course_score int/*学分*/
);
/*课程专业设置表*/
create table course_major(
	cm_courseid varchar(32),
	cm_majorid varchar(32),
	constraint cm_pk primary key(cm_courseid,cm_majorid),
	constraint cm_fk1 foreign key(cm_courseid) references course(course_id),
	constraint cm_fk2 foreign key(cm_majorid) references major(major_id)
);

/*班级*/
create table clazz(
   clazz_id varchar(32) primary key,
   clazz_name varchar(50),
   clazz_gradeid varchar(32),/*引用年级ID*/
   clazz_majorid varchar(32),/*引用专业ID*/
   constraint clazz_fk1 foreign key(clazz_gradeid) references grade(grade_id),
   constraint clazz_fk2 foreign key(clazz_majorid) references major(major_id)
);

/*学生名单花名册*/
create table stud(
	stud_id varchar(32) primary key,
	stud_name varchar(50),
	stud_no varchar(50) unique,/*学号*/
	stud_sex char(1),
	stud_age int,
	stud_tel varchar(100),
	stud_clazzid varchar(32)/*引用班级id*/
	,
	constraint stud_fk foreign key(stud_clazzid) references clazz(clazz_id)
);

/*成绩管理*/
create table score(
	score_id varchar(32) primary key,
	score_studid varchar(32),/*引用学生id*/
	constraint score_fk1 foreign key(score_studid) references stud(stud_id),
	score_courseid varchar(32),/*引用课程id*/
	constraint score_fk2 foreign key(score_courseid) references course(course_id),
	score_score numeric(5,2),/*分数*/
	score_term varchar(50),/*第几学期共8个学期*/
	score_date varchar(19)/*时间用户选择的时间*/
);

