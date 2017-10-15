package cn.grades.studs.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Clas;
import cn.grades.domain.Major;
import cn.grades.domain.Stud;

public interface IStudsDao {

	Map<String, Object> query(int page, int rows);

	Stud save(Stud s);

	int delete(String id);

	int update(Stud s);


	List<Clas> selectClass();

	Map<String, Object> select(Stud s);

	

}