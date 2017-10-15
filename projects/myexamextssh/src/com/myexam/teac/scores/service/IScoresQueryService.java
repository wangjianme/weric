package com.myexam.teac.scores.service;

import java.util.Map;

/**
 * 查询学生成绩
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public interface IScoresQueryService {
	/**
	 * 根据参数查询学生的考试分数
	 * @param param
	 * @return
	 */
	Map<String,Object> queryScores(Map<String,Object> param);
}
