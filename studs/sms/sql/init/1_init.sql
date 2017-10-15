/*测试的用户*/
insert into users
values('U001','Jack','87e2a1e158d653c8063ce1de3744d65b',
'd22e18e2b82f4fef97541f5c18556a24','1');

/*测试的角色*/
insert into roles(role_id,role_name) values('R001','管理员');

/*测试的菜单*/
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M001','财大',null,'closed',null);
 
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M002','安全管理',null,'closed','M001');

insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M003','修改密码','/manager/safe/pwd.html','open','M002');
 
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M004','角色管理','/manager/role/roles.html','open','M002');

insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M005','用户管理','/manager/user/user.html','open','M002');



/*年级设置*/
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M006','专业设置',null,'closed','M001'); 

insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M007','年级设置','/manager/grade.html','open','M006');
 
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M008','专业设置','/manager/major.html','open','M006');
 
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M009','课程设置','/manager/course.html','open','M006');

/*学生管理*/
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M100','学生管理',null,'closed','M001');
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M101','花名册','/manager/stud.html','open','M100');
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M102','班级管理','/manager/clazz.html','open','M100');
insert into menus(menu_id,menu_text,menu_res,menu_state,menu_parent)
 values('M103','成绩管理','/manager/score.html','open','M100'); 
 
/*创建关系*/
insert into user_role values('U001','R001');
/*角色与菜单的关系*/
insert into role_menu(rm_menuid,rm_roleid) values('M001','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M002','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M003','R001');

insert into role_menu(rm_menuid,rm_roleid) values('M004','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M005','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M006','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M007','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M008','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M009','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M100','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M101','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M102','R001');
insert into role_menu(rm_menuid,rm_roleid) values('M103','R001');


/*课程信息*/
insert into grade(grade_id,grade_name,grade_date) values('G001','2013级','2012-12-12');

insert into major(major_id,major_name) values('M001','信息管理专业');

insert into grade_major(gm_gradeid,gm_majorid) values('G001','M001');

insert into course(course_id,course_name,course_score) values('C001','Oracle课程',4);

insert into course_major(cm_courseid,cm_majorid) values('C001','M001');

insert into clazz(clazz_id,clazz_name,clazz_gradeid,clazz_majorid) values('CLAZZ001','信息一班','G001','M001');
 
/*学生名册*/
insert into stud(stud_id,stud_name,stud_no,stud_sex,stud_age,stud_clazzid)
values('S001','赵丹','S001001','0',18,'CLAZZ001');