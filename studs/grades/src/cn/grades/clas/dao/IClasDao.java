package cn.grades.clas.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Clas;
import cn.grades.domain.Department;
import cn.grades.domain.Grade;
import cn.grades.domain.Major;

public interface IClasDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.grades.clas.dao.IClasDao#query()
	 */
	Map<String, Object> query(int page, int rows);

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.grades.clas.dao.IClasDao#save(cn.grades.domain.Clas)
	 */
	Clas save(Clas clas);

	int update(Clas clas);

	List<Department> text();

	List<Major> majorVal();

	List<Grade> name();
   Map<String, Object> select (Clas clas,int page,int rows);
	
}