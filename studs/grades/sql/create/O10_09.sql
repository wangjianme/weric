create table course(
	course_id varchar(32) primary key,/*课程id*/
	course_name varchar(30),/*课程名*/
	course_score int,/*学分*/
    course_must char(1),/*是否是必修课*/
    course_pub  char(1),/*是否是公开课*/
    course_desc varchar(500)
);
create table course_dept(
cd_courseid varchar(32),
cd_deptid varchar(32),
constraint cd_pk primary key(cd_courseid,cd_deptid),
constraint cd_fk1 foreign key(cd_courseid) references course(course_id),
constraint cd_fk2 foreign key(cd_deptid) references departments(dept_id)
);
create table course_major(
cm_courseid varchar(32),
cm_majorid varchar(32),
constraint cm_pk primary key(cm_courseid,cm_majorid),
constraint cm_fk1 foreign key(cm_courseid) references course(course_id),
constraint cm_fk2 foreign key(cm_majorid) references major(major_id)
);

create table major_grade(
mg_id varchar(32) primary key,
mg_majorid varchar(32),/*引用major_id*/
mg_gradeid varchar(32),/*引用grade_id*/
constraint mg_fk1 foreign key(mg_majorid) references major(major_id),
constraint mg_fk2 foreign key(mg_gradeid) references grades(grade_id)
);

create table course_grade(
cg_courseid varchar(32),
cg_gradeid varchar(32),
cg_mgid varchar(32),/*引用major_grade的mg_id*/
constraint cg_pk primary key(cg_courseid,cg_gradeid,cg_mgid),
constraint cg_fk1 foreign key(cg_courseid) references course(course_id),
constraint cg_fk2 foreign key(cg_gradeid) references grades(grade_id),
constraint cg_fk3 foreign key(cg_mgid) references major_grade(mg_id)
);