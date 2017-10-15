/*写入角色*/
insert into role(role_id,role_name,role_candel,role_desc) values('1','管理员','0','超级管理员');
/*管理员,名称可以是中文*/
insert into teac(teac_id,teac_name,teac_pwd,teac_sex) values('1','admin','e03647fa853d42bbd5464d43463f9690','1');
/*菜单*/
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('1','系统管理','ROOT','0',null,null,'1',1);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('2','组织架构','1','1','dept.action','_main','1',1);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('A1','学制管理','1','1','edusys.action','_main','1',2);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('3','专业','1','1','major.action','_main','1',3);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('4','查看题型','1','1','cate.action','_main','1',4);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('5','课程管理','1','1','cour.action','_main','1',5);

/***************/
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('6','考试管理','ROOT','0',null,null,'1',2);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('7','题库','6','1','ques.action','_main','1',1);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('8','试卷管理','6','1','examset.action','_main','1',2);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('81','考试安排','6','1','examarrange.action','_main','1',2);	
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('9','补考管理','6','1','lostexam.action','_main','1',3);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('10','考试监控','6','1','exammonitor.action','_main','1',4);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('11','在线阅卷','6','1','onlinerate.action','_main','1',5);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('12','成绩查询','6','1','scoresquery.action','_main','1',6);
/*
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('13','成绩分析','6','1','fx.html','_main','1',7)
*/
/***************/	
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('14','学生管理','ROOT','0',null,null,'1',3);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('15','班级管理','14','1','class.action','_main','1',1);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('16','学生管理','14','1','studmanager.action','_main','1',2);
/*****************/
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('17','安全','ROOT','0',null,null,'1',3);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('18','修改密码','17','1','teacpwd.action','_main','1',1);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('19','角色定义','17','1','roleAction!init.action','_main','1',2);
/*
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys)
	values('20','功能定义','17','1','func.html','_main','1')
*/
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('21','用户管理','17','1','teac.action','_main','1',3);
/*log.....*/
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('log','日志','ROOT','0',null,null,'1',4);
insert into menu(menu_id,menu_text,menu_parent,menu_leaf,menu_url,menu_target,menu_issys,menu_order)
	values('log01','查看日志','log','1','log.html','_main','1',1);

/*用户角色对照*/
insert into teacrole(tc_teac,tc_role) values('1','1');
/*角色菜单对照*/

insert into func(func_role,func_menu) values('1','1');
insert into func(func_role,func_menu) values('1','2');
insert into func(func_role,func_menu) values('1','A1');
insert into func(func_role,func_menu) values('1','3');
insert into func(func_role,func_menu) values('1','4');
insert into func(func_role,func_menu) values('1','5');

insert into func(func_role,func_menu) values('1','6');
insert into func(func_role,func_menu) values('1','7');
insert into func(func_role,func_menu) values('1','8');
insert into func(func_role,func_menu) values('1','81');
insert into func(func_role,func_menu) values('1','9');
insert into func(func_role,func_menu) values('1','10');

insert into func(func_role,func_menu) values('1','11');
insert into func(func_role,func_menu) values('1','12');
/*
insert into func(func_role,func_menu) values('1','13')
*/
insert into func(func_role,func_menu) values('1','14');
insert into func(func_role,func_menu) values('1','15');

insert into func(func_role,func_menu) values('1','16');
insert into func(func_role,func_menu) values('1','17');
insert into func(func_role,func_menu) values('1','18');
insert into func(func_role,func_menu) values('1','19');
/*
insert into func(func_role,func_menu) values('1','20')
*/
insert into func(func_role,func_menu) values('1','21');
