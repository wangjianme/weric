package com.myexam.teac.examview.dao;

import java.util.Map;

/**
 * 某个试卷的预览功能
 * @author wangjianme
 * @version 1.0,2010-11-1
 */
public interface IExamviewDao {
	/**
	 * 查询此试卷的信息
	 * @param param
	 * @return
	 */
	Map<String,Object> view(Map<String,Object> param);
	/**
	 * 根据用户给定的试题id，返回此试题的详细信息
	 */
	Map<String,Object> queryOneExamQues(String examQuesId);
}
