package com.myexam.teac.scores.service;

import java.util.Map;

import com.myexam.teac.scores.dao.IScoresQueryDao;

/**
 * 查询学生的考试分数
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public class ScoresQueryServiceImpl implements IScoresQueryService {
	private IScoresQueryDao dao;

	public IScoresQueryDao getDao() {
		return dao;
	}

	public void setDao(IScoresQueryDao dao) {
		this.dao = dao;
	}
	/**
	 * 根据参数查询学生的考试分数
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryScores(Map<String,Object> param){
		return getDao().queryScores(param);
	}
}
