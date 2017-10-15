package cn.grades.major.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Major;

public interface IMajorDao {

	Map<String, Object> query();

	Map<String, Object> select(Major major);

	List<Major> selectDept();

	List<Major> selectMajor();

	int delete(String id);

	int save(Major major);

}