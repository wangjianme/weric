/**
 * 测试用户密码：1234
 */
insert into users(user_id,user_name,user_pwd,user_salt)
values('U001','Admin','9df8b7f76eec69de1c52f7f44d5aec71','83fc9f3cca2f4487b0c478d55757e0b8');
/*
 * 菜单资源
 */
insert into menus values('M001','安全管理',null,'closed',null);
insert into menus values('M002','用户管理','manager/user/user.html','open','M001');
insert into menus values('M003','角色管理','manager/role/role.html','open','M001');
insert into menus values('M004','修改密码(完成)','manager/pwd/pwd.html','open','M001');

insert into menus values('M005','组织架构管理',null,'closed',null);
insert into menus values('M017','学院设置','manager/dept/college.html','open','M005');
insert into menus values('M006','教师管理(完成)','manager/dept/teacher.html','open','M005');
insert into menus values('M007','职称管理(完成)','manager/title/title.html','open','M005');
insert into menus values('M018','学历管理(完成)','manager/dept/degrees.html','open','M005');


insert into menus values('M008','专业课程管理',null,'closed',null);
insert into menus values('M009','年级设置(完成)','/manager/grade/grade.html','open','M008');
insert into menus values('M019','专业设置(完成)','/manager/major/major.html','open','M008');
insert into menus values('M010','班级设置(完成)','manager/major/clas.html','open','M008');
insert into menus values('M011','课程设置','manager/course/course.html','open','M008');
insert into menus values('M012','学生管理','manager/major/stud.html','open','M008');
insert into menus values('M013','成绩管理','manager/major/score.html','open','M008');

insert into menus values('M014','统计信息',null,'closed',null);
/*
 *使用echar.js/Highcharts-4.0.3 
 * 按年龄统计，学期，专业，学院
 */
insert into menus values('M015','成绩统计','/manager/coll/score.html','open','M014');
/**
 * 按职称
 * 学历3 
 */
insert into menus values('M016','教师统计(完成)','manager/teacherCensus/teacherCensus.html','open','M014');

/**
 * 测试的菜单
 */
insert into menus values('M900','测试',null,'closed',null);
insert into menus values('M901','示例项目','test/example.html','open','M900');

/**
 * 声明一个角色
 */
insert into roles values('R001','管理员');


/**
 * 设置关系
 */
insert into user_role values('U001','R001');
insert into role_menu values('R001','M001');
insert into role_menu values('R001','M002');
insert into role_menu values('R001','M003');
insert into role_menu values('R001','M004');
insert into role_menu values('R001','M005');
insert into role_menu values('R001','M006');
insert into role_menu values('R001','M007');
insert into role_menu values('R001','M008');
insert into role_menu values('R001','M009');
insert into role_menu values('R001','M010');
insert into role_menu values('R001','M011');
insert into role_menu values('R001','M012');
insert into role_menu values('R001','M013');
insert into role_menu values('R001','M014');
insert into role_menu values('R001','M015');
insert into role_menu values('R001','M016');
insert into role_menu values('R001','M017');
insert into role_menu values('R001','M018');
insert into role_menu values('R001','M019');
/**
 * 测试
 */
insert into role_menu values('R001','M900');
insert into role_menu values('R001','M901');
/*
 * 学院资源
 */
insert into departments values('C001','理学院','closed',null,'1','xx楼432号','523545453','icon-onedept',100);
insert into departments values('C002','党委','open','C001','0','xx楼432号','231312313','icon-twodept',11);
insert into departments values('C003','教务处','open','C001','0','xx楼442号','321312313','icon-twodept',12);
insert into departments values('C004','组织部','open','C001','0','xx楼432号','781238613','icon-twodept',21);
insert into departments values('C005','财务处','open','C001','0','xx楼432号','231312313','icon-twodept',22);
insert into departments values('C006','艺术学院','closed',null,'1','xx楼432号','523545453','icon-onedept',100);
insert into departments values('C007','党委','open','C006','0','xx楼432号','231312313','icon-twodept',11);
insert into departments values('C008','教务处','open','C006','0','xx楼442号','321312313','icon-twodept',12);
insert into departments values('C009','组织部','open','C006','0','xx楼432号','781238613','icon-twodept',21);
insert into departments values('C010','财务处','open','C006','0','xx楼432号','231312313','icon-twodept',22);
insert into departments values('C011','信息学院','closed',null,'1','xx楼432号','523545453','icon-onedept',100);
insert into departments values('C012','党委','open','C011','0','xx楼432号','231312313','icon-twodept',11);
insert into departments values('C013','教务处','open','C011','0','xx楼442号','321312313','icon-twodept',12);
insert into departments values('C014','组织部','open','C011','0','xx楼432号','781238613','icon-twodept',21);
insert into departments values('C015','财务处','open','C011','0','xx楼432号','231312313','icon-twodept',22);


