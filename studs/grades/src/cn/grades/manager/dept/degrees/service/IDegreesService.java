package cn.grades.manager.dept.degrees.service;


import java.util.List;

import cn.grades.domain.Degrees;


public interface IDegreesService {


	List<Degrees> query();
	public Degrees save(Degrees degrees);
	public int delete(String id);
	public int update(Degrees degrees);
}
