package cn.grades.manager.coll.dao;

import java.util.List;

import cn.grades.domain.CollScore;
import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Major;

public interface ICollScoreDao {

	List<CollScore> query();

	List<Department> ins();

	List<Degrees> edu();

	List<Major> majorVal();

	List<Major> selectGrade();

	List<Major> selectMajor(String deptid);

	List<Major> selectDept(String deptid1);

}