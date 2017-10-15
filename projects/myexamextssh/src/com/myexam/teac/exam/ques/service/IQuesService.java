package com.myexam.teac.exam.ques.service;

import java.util.Map;

/**
 * 题库管理
 * @author wangjianme
 * @version 1.0,2010-10-25
 */
public interface IQuesService {
	public Map<String,Object> query(Map<String,Object> param);
	public Map<String,Object> queryCour(Map<String,Object> param);
	public Map<String,Object> save(Map<String,Object> param);
	public Map<String,Object> queryOneQues(String quesId);
	public Map<String,Object> del(String quesId);
}
