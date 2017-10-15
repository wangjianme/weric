package cn.grades.studs.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Clas;
import cn.grades.domain.Stud;

public interface IStudsService {

	Map<String, Object> query(int page, int rows);

	Stud save(Stud s);

	int delete(String id);

	int update(Stud s);

	List<Clas> selectClass();

}