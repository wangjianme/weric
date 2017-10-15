package com.myexam.stud.studscore.service;

import java.util.Map;

import com.myexam.stud.studscore.dao.IStudScoreDao;

/**
 * 学生查询自己的历史成绩
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public class StudScoreServiceImpl implements IStudScoreService{
	private IStudScoreDao dao;
	public IStudScoreDao getDao() {
		return dao;
	}
	public void setDao(IStudScoreDao dao) {
		this.dao = dao;
	}
	/**
	 * 查询出学生的历史成绩
	 */
	public Map<String, Object> queryHistoryScore(Map<String, Object> param) {
		return getDao().queryHistoryScore(param);
	}
}
