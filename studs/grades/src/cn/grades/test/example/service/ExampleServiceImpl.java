package cn.grades.test.example.service;

import cn.grades.domain.Example;
import cn.grades.test.example.dao.ExampleDaoJdbc;
import cn.grades.test.example.dao.IExampleDao;
import java.util.Map;

public class ExampleServiceImpl implements IExampleService {
	private IExampleDao dao = new ExampleDaoJdbc();
	public Map<String,Object> query(int page,int rows){
		return dao.query(page, rows);
	}
	public Example save(Example exp)
	{
		return dao.save(exp);
	}
}
