package com.myexam.teac.examarrange.service;

import java.util.Map;

public interface IExamArrangeService {
	/**
	 * 查询所有的试卷
	 * @param param
	 * @return
	 */
	Map<String,Object> queryExam(Map<String,Object> param);
	/**
	 * 根据examid查询出已经安排了考试的学员信息和所属于的班级
	 */
	Map<String,Object> queryOpendStuds(Map<String,Object> param);
	/**
	 * 根据班级开通
	 */
	Map<String,Object> saveFromCls(Map<String,Object> param);
	/**
	 * 修改学生信息,主要指修改禁止考试或是允许考试
	 */
	Map<String,Object> updateState(Map<String,Object> param);
	/**
	 * 根据用户选择的记录一次删除多行
	 */
	Map<String,Object> deleteStuds(Map<String,Object> param);
}
