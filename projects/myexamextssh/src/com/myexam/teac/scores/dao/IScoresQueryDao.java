package com.myexam.teac.scores.dao;

import java.util.Map;

/**
 * 查询学生的考试分数
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public interface IScoresQueryDao {
	/**
	 * 根据参数查询学生的考试分数
	 * @param param
	 * @return
	 */
	Map<String,Object> queryScores(Map<String,Object> param);
}
