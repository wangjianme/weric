insert into dept(dept_id,dept_name,dept_parent,dept_isleaf,dept_desc) values('1','学院','ROOT','0','');
insert into dept(dept_id,dept_name,dept_parent,dept_isleaf,dept_desc) values('2','教学管理部','1','1','');
insert into dept(dept_id,dept_name,dept_parent,dept_isleaf,dept_desc) values('3','学生管理部','1','1','');
insert into dept(dept_id,dept_name,dept_parent,dept_isleaf,dept_desc) values('4','校区','1','1','');
/*题目类型*/
insert into cate(cate_id,cate_name,cate_order,cate_key,cate_desc) values('1','填空题',1,'text','在指定的位置填入正确的文字');
insert into cate(cate_id,cate_name,cate_order,cate_key,cate_desc) values('2','判断题',2,'radio','只有两个可选答案，正确或是错误');
insert into cate(cate_id,cate_name,cate_order,cate_key,cate_desc) values('3','单选题',3,'radio','只允许有一个正确答案的选择题');
insert into cate(cate_id,cate_name,cate_order,cate_key,cate_desc) values('4','多选题',4,'checkbox','一个或是多个答案的选择题');
insert into cate(cate_id,cate_name,cate_order,cate_key,cate_desc) values('5','主观题',5,'textarea','可以包含多种形式，如简答、编程、上传作品图片等');