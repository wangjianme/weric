package cn.grades.major.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Major;
import cn.grades.major.dao.IMajorDao;
import cn.grades.major.dao.MajorDaoJdbc;

public class MajorService implements IMajorService {
private IMajorDao dao=new MajorDaoJdbc();
	
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.service.IMajorService#query()
	 */
	@Override
	public Map<String, Object> query(){
		return dao.query();
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.service.IMajorService#select(cn.grades.domain.Major)
	 */
	@Override
	public Map<String, Object> select(Major major){
		return dao.select(major);
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.service.IMajorService#selectDept()
	 */
	@Override
	public List<Major> selectDept() {
		return dao.selectDept();
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.service.IMajorService#selectMajor()
	 */
	@Override
	public List<Major> selectMajor() {
		return dao.selectMajor();
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.service.IMajorService#delete(java.lang.String)
	 */
	@Override
	public int delete(String id) {
		return dao.delete(id);
	}
	/* (non-Javadoc)
	 * @see cn.grades.manager.major.service.IMajorService#save(cn.grades.domain.Major)
	 */
	@Override
	public int save(Major  major) {
		return dao.save(major);
	}
}
