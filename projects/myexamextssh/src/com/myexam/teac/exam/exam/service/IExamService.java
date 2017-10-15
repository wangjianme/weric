package com.myexam.teac.exam.exam.service;

import java.util.Map;

public interface IExamService {
	Map<String,Object> queryExam(Map<String,Object> param);
	Map<String,Object> queryCour(Map<String,Object> param);
	/**
	 * 修改试卷状态
	 */
	Map<String,Object> updateState(Map<String,Object> param);
	public Map<String, Object> delExam(String examId);
}
