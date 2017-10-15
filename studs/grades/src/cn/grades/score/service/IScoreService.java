package cn.grades.score.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Course;
import cn.grades.domain.Grade;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;

public interface IScoreService {

	Map<String, Object> query(int page, int rows);

	Score save(Score sc);

	
	List<Stud> query1();
	List<Course> query2();

     int delete(String id);
     int delete1(String studid);
     int delete2(String courseid);

     int update(Score sc);
 	Map<String, Object> select(Score score);
}