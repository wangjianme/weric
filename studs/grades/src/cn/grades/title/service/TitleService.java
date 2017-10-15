package cn.grades.title.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Title;
import cn.grades.title.dao.TitleDaoJdbc;
import cn.grades.domain.Title;

public class TitleService implements ITitleService {
	private TitleDaoJdbc dao = new TitleDaoJdbc();

	public List<Title> query() {
		return dao.query();
	}
	
	public Title save(Title title) {
		return dao.save(title);
	}

	public int delete(String id) {
		return dao.delete(id);
	}
	
	public int update(Title title){
		return dao.update(title);
	}
	
	public List<Title> select(Title title){
		return dao.select(title);
	}
}
