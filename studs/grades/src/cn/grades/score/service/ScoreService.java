package cn.grades.score.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Course;
import cn.grades.domain.Example;
import cn.grades.domain.Grade;
import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.score.dao.IScoreDao;
import cn.grades.score.dao.ScoreDaoJdbc;
import cn.grades.test.example.dao.ExampleDaoJdbc;
import cn.grades.test.example.dao.IExampleDao;

public class ScoreService implements IScoreService {
	private IScoreDao dao = new ScoreDaoJdbc();
	/* (non-Javadoc)
	 * @see cn.grades.score.service.IScoreService#query(int, int)
	 */
	@Override
	public Map<String,Object> query(int page,int rows){
		return dao.query(page, rows);
	}
	/* (non-Javadoc)
	 * @see cn.grades.score.service.IScoreService#save(cn.grades.domain.Score)
	 */
	@Override
	public Score save(Score sc)
	{
		return dao.save(sc);
	}
	/* (non-Javadoc)
	 * @see cn.grades.score.service.IScoreService#selcet(cn.grades.domain.Score)
	 */

	public List<Stud> query1()
	{
		return dao.query1();
	}
	public List<Course> query2()
	{
		return dao.query2();
	}
	public int delete(String id){
		return dao.delete(id);
	}
	
	
	public int delete1(String studid){
		return dao.delete1(studid);
	}
	
	
	public int delete2(String courseid){
		return dao.delete1(courseid);
	}
	public int update(Score sc){
		return dao.update(sc);
	}
	 public Map<String, Object> select(Score score){
		return dao.select(score);
	}
	
	
	
}
