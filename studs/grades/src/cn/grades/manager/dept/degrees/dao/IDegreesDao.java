package cn.grades.manager.dept.degrees.dao;


import java.util.List;

import cn.grades.domain.Degrees;



public interface IDegreesDao {


	List<Degrees> query();
	public Degrees save(Degrees degrees);
	public int delete(String id);
	public int update(Degrees degrees);
}