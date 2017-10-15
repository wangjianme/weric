package cn.sms.grade.service;

import java.util.List;
import java.util.Map;

import cn.sms.domain.Grade;
import cn.sms.grade.dao.GradeDao;

public class GradeService {
	private GradeDao dao = new GradeDao();
	public Map<String, Object> query(int rows,int page){
		return dao.query(rows, page);
	}
	
	public int delete(String id){
		return dao.delete(id);
	}
	public List<Grade> query() {
		return dao.query();
	}
}
