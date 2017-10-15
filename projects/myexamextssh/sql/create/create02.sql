/*题库设置*/
create table ques(
	ques_id varchar(32) not null,
	ques_title varchar(200),	/*知识点或是简单说明，用于显示*/
	ques_body varchar(4000),
	ques_choose varchar(1000),
	ques_cate varchar(32),		/*引用cate_id*/
	ques_cour varchar(32),		/*引用cour_id*/
	ques_solu varchar(200),
	ques_score int,
	ques_note varchar(200),
	ques_level char(1),
	ques_time varchar(19),
	ques_maker varchar(50),
	ques_state char(1)
);
alter table ques
    add constraint ques_pk primary key(ques_id);
alter table ques
	add constraint ques_fk1 foreign key(ques_cate) references cate(cate_id);
alter table ques
	add constraint ques_fk2 foreign key(ques_cour) references cour(cour_id);
/*#######################考试设置###############################*/
/*试卷设置*/

create table exam(
	exam_id varchar(32) not null,
	exam_name varchar(50),
	exam_cour varchar(32), /*引用课程cour_id*/
	exam_mktime varchar(19),
	exam_note varchar(200),
	exam_time int,
	exam_maker varchar(30),
	exam_score int,
	exam_state char(1),
	exam_pass int
);
alter table exam
	add constraint exam_pk primary key(exam_id);
alter table exam
	add constraint exam_fk2 foreign key(exam_cour) references cour(cour_id);
/*试卷题型对照*/
create table examcate(
	ec_id varchar(32) not null,
	ec_exam varchar(32) not null,
	ec_cate varchar(32) not null,
    ec_catename varchar(200),
	ec_seq  int,
	ec_score int,
	ec_qty int,
	ec_order char(1)
);
alter table examcate
	add constraint ec_pk primary key(ec_id);
alter table examcate
	add constraint ec_fk1 foreign key(ec_exam) references exam(exam_id);
/*试卷题目表*/
create table examques(
	eq_id varchar(32) not null,
	eq_quesid varchar(32),
	eq_ecid  varchar(32),	
	eq_cate varchar(32),
	eq_seq  int,
	eq_title varchar(200),
	eq_body varchar(2000),
	eq_solu varchar(200),
	eq_choose varchar(1000),
	eq_note varchar(200),
	eq_level char(1),
	eq_maker varchar(20),
	eq_score int
);
alter table examques
	add constraint eq_pk primary key(eq_id);
alter table examques
	add constraint eq_fk1 foreign key(eq_ecid) references examcate(ec_id);
/*开通试卷*/
create table openexam(
	oe_id varchar(32) not null,
	oe_exam varchar(32) not null,
	oe_stud  varchar(32) not null,
	oe_state char(1),
	oe_teac varchar(50),
	oe_rate varchar(50),
	oe_time varchar(19),
	oe_type int,
	oe_info varchar(32)
);
alter table openexam
	add constraint oe_pk primary key(oe_id);
alter table openexam
	add constraint oe_fk1 foreign key(oe_exam) references exam(exam_id);
alter table openexam
	add constraint oe_fk2 foreign key(oe_stud) references stud(stud_id);

/*补考对应一个学生*/
/*2010.11.26号删除
create table lostexam(
  le_id varchar(32) not null,
  le_info varchar(32),
  le_exam varchar(32),
  le_stud varchar(32),
  le_state char(1),
  le_teac varchar(32),
  le_rate varchar(32),
  le_time varchar(19),
  le_times int
);
alter table lostexam
  add constraint le_pk primary key(le_id);
*/
/*在线考试功能*/
/*考试信息表*/
create table info(
	info_id varchar(32) not null,
	info_oe varchar(32),
	info_stud varchar(32),
	info_btime varchar(19),
	info_etime varchar(19),
	info_score numeric(5,2),
	info_rate varchar(50),
	info_state char(1),
	info_timein int,
	info_type int,
	info_resit char(1)
);
alter table info
	add constraint info_pk primary key(info_id);
alter table info
 	add constraint info_fk1 foreign key(info_oe) references openexam(oe_id);
alter table info
	add constraint info_fk2 foreign key(info_stud) references stud(stud_id);
/*考试题目结果*/
create table answ(
    answ_id varchar(32) not null,
	answ_info varchar(32) not null,
	answ_eq	varchar(32) not null,
	answ_answer varchar(4000),
	answ_score int
);
alter table answ
	add constraint answ_pk primary key(answ_id);
