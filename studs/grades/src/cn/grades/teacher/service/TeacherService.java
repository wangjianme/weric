package cn.grades.teacher.service;

import java.util.List;
import java.util.Map;
import cn.grades.domain.Degrees;
import cn.grades.domain.Department;
import cn.grades.domain.Teacher;
import cn.grades.domain.Title;
import cn.grades.teacher.dao.ITeacherDao;
import cn.grades.teacher.dao.TeacherDaojdbc;

public class TeacherService implements ITeacherService {
	private ITeacherDao dao = new TeacherDaojdbc();
	@Override
	public Map<String, Object> query(int page, int rows) {
		return dao.query(page, rows);
	}

	@Override
	public Teacher save(Teacher teacher) {
		return dao.save(teacher);
	}
	@Override
	public int delete(String id) {

		return dao.delete(id);
	}
	@Override
	public int update(Teacher teacher) {

		return dao.update(teacher);
	}

	@Override
	public List<Department> ins() {
		return dao.ins();
	}

	@Override
	public List<Degrees> edu() {
		return dao.edu();
	}

	@Override
	public List<Title> rank() {
		return dao.rank();
	}

	
	public Map<String, Object> select(Teacher teacher) {
		// TODO Auto-generated method stub
		return dao.select(teacher);
	}

	}

	



