package com.myexam.stud.studscore.service;

import java.util.Map;

/**
 * 学生查询自己的历史成绩
 * @author wangjianme
 * @version 1.0,2010-12-2
 */
public interface IStudScoreService {
	/**
	 * 查询出学生的历史成绩
	 */
	Map<String,Object> queryHistoryScore(Map<String,Object> param);
}
