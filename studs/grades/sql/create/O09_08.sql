/*班级*/ 
CREATE TABLE clas(
   clas_id VARCHAR(32) PRIMARY KEY,
   clas_name VARCHAR(50),
   clas_des  VARCHAR(300),
   clas_gradeid VARCHAR(32),/*引用年级ID*/
   clas_majorid VARCHAR(32),/*引用专业ID*/
   clas_deptid VARCHAR(32),/*引用学院ID*/
   CONSTRAINT clas_fk1 FOREIGN KEY(clas_gradeid) REFERENCES grades(grade_id),
   CONSTRAINT clas_fk2 FOREIGN KEY(clas_majorid) REFERENCES major(major_id),
   CONSTRAINT clas_fk3 FOREIGN KEY(clas_deptid) REFERENCES departments(dept_id)
);
