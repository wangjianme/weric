/*年级设置表*/
create table grades(
	grade_id varchar(32) primary key,/*年级id*/
	grade_name varchar(50) unique,/*年级名称*/
	grade_date varchar(19),/*年级生成时间 ， 如果学生的入学时间与该时间不匹配，说明该学生为插班生（留级或跳级）*/
	grade_desc varchar(200)/*说明*/
); 
