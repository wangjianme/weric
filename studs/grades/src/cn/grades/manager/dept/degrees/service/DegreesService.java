package cn.grades.manager.dept.degrees.service;


import java.util.List;

import cn.grades.domain.Degrees;
import cn.grades.manager.dept.degrees.dao.DegreesDaoJdbc;
import cn.grades.manager.dept.degrees.dao.IDegreesDao;


public class DegreesService implements IDegreesService {

	private IDegreesDao dao = new DegreesDaoJdbc();
	public List<Degrees> query(){
		return dao.query();
	}
	public Degrees save(Degrees degrees) {
		return dao.save(degrees);
	}
	
	public int delete(String id){
		return dao.delete(id);
	}
	public int update(Degrees degrees) {
		return dao.update(degrees);
	}
}
