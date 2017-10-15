package cn.grades.clas.service;

import java.util.List;
import java.util.Map;

import cn.grades.clas.dao.ClasDaoJdbc;
import cn.grades.domain.Clas;
import cn.grades.domain.Department;
import cn.grades.domain.Grade;
import cn.grades.domain.Major;

public class ClasService implements IClasService {
	private ClasDaoJdbc dao = new ClasDaoJdbc();
	public Map<String, Object> query(int page,int rows){
		return dao.query(page,rows);
	}

	@Override
	public Clas save(Clas clas) {
		return dao.save(clas);
	}

	@Override
	public List<Department> text() {
		return dao.text();
	}

	@Override
	public List<Major>majorVal (){
		return dao.majorVal();
	}
	@Override
	public List<Grade>name () {
		return dao.name();
	}
	public int delete(String id) {
		return dao.delete(id);
	}

	@Override
	public int update(Clas clas){
		return dao.update(clas);
	}
	public Map<String, Object> select(Clas clas, int row, int pag) {
		return dao.select(clas, row, pag);
	}
}
