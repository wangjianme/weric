/*教师管理模块*/
CREATE TABLE teachers(    /*创建teachers表*/
teacher_id VARCHAR(32) NOT NULL,    /*id,不能为空*/
teacher_name VARCHAR(30) NOT NULL,    /*姓名,不能为空*/
teacher_sex CHAR(1),   /*性别,0:女,1:男*/
teacher_tel VARCHAR(20),     /*电话号码*/
/*以下是三个外键*/
teacher_deptid VARCHAR(32),    /*学院,引用学院管理*/
teacher_degreesid VARCHAR(32), /*学历*/
teacher_titleid VARCHAR(32),    /*职称*/
teacher_major VARCHAR(30),   /*学科方向*/
teacher_date date,/*教师入职时间*/
PRIMARY KEY(teacher_id),  /*设置id为主码*/
constraint c_fk2 foreign key(teacher_deptid) references departments(dept_id),
constraint c_fk1 foreign key(teacher_titleid) references title(title_id),
constraint c_fk3 foreign key(teacher_degreesid) references degrees(degrees_id)
);
