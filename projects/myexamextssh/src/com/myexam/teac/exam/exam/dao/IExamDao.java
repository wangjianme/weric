package com.myexam.teac.exam.exam.dao;

import java.util.Map;

/**
 * 试卷管理
 * @author wangjianme
 * @version 1.0,2010-10-29
 */
public interface IExamDao {
	/**
	 * 查询试卷
	 * @param param
	 * @return
	 */
	Map<String,Object> queryExam(Map<String,Object> param);
	/**
	 * 查询课程
	 */
	Map<String,Object> queryCour(Map<String,Object> param);
	/**
	 * 修改试卷状态
	 */
	Map<String,Object> updateState(Map<String,Object> param);
	/**
	 * 删除
	 */
	Map<String,Object> delExam(String examId);
}
