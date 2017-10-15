CREATE TABLE scores (
  score_id VARCHAR(32) ,
  score_studid VARCHAR(32) ,
  score_courseid VARCHAR(32) ,
  score_score NUMERIC(5,2),
  score_term INT,
  score_type   CHAR(1),/*0,1,2,3*/
  score_datetime VARCHAR(19),
  score_teacher VARCHAR(50),/*直接保存老师 名称*/
  CONSTRAINT si_pk PRIMARY KEY(score_id),
  CONSTRAINT si_fk1 FOREIGN KEY(score_studid) REFERENCES studs(stud_id),
  CONSTRAINT si_fk2 FOREIGN KEY(score_courseid) REFERENCES course(course_id)
);