/**
 * 学生表
 */
create table studs(
   stud_id varchar(32) primary key,/*id*/
   stud_no varchar(32),/*学号*/
   stud_name varchar(32),/*姓名*/
   stud_sex CHAR(1),   /*性别,0:女,1:男*/
   stud_idcard varchar(32),/*身份证*/
   stud_email varchar(32),/*邮箱*/
   stud_age int,/*年龄*/
   stud_pwd varchar(32),/*初始密码*/
   stud_tel varchar(32),/*手机*/
   stud_addr varchar(32),/*住址*/
   stud_height int,/*身高*/
   stud_wight int,/*体重*/
   stud_qq int,/*qq*/
   stud_minzu varchar(32),/*民族*/
   stud_jibie CHAR(1),/*0群众1团员2党员*/
   stud_clasid varchar(32),/*sc001*/
   stud_salt varchar(32),
   constraint stud_fk1 foreign key(stud_clasid) references clas(clas_id)
);