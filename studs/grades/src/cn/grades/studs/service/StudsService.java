package cn.grades.studs.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Clas;
import cn.grades.domain.Stud;
import cn.grades.studs.dao.IStudsDao;
import cn.grades.studs.dao.StudsDaoJbdc;

public class StudsService implements IStudsService  {
	private IStudsDao dao =  new StudsDaoJbdc();
	public Map<String,Object> query(int page,int rows){
		return dao.query(page, rows);
	}
	public Stud save(Stud s)
	{
		return dao.save(s);
	}
	
	public int delete(String id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}
	public int update (Stud s) {
		return dao.update(s);
	}
	@Override
	public List<Clas> selectClass() {
		return dao.selectClass();
	}
	public Map<String, Object> select(Stud s){
		return dao.select(s);
	}
}
