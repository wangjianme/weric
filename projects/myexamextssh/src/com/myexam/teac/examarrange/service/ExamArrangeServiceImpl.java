package com.myexam.teac.examarrange.service;

import java.util.Map;

import com.myexam.teac.examarrange.dao.IExamArrangeDao;
/**
 * @author wangjianme
 * @version 1.0,2010-11-4
 */
public class ExamArrangeServiceImpl implements IExamArrangeService{
	private IExamArrangeDao dao;
	public IExamArrangeDao getDao() {
		return dao;
	}
	public void setDao(IExamArrangeDao dao) {
		this.dao = dao;
	}
	/**
	 * 查询所有的试卷
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryExam(Map<String, Object> param) {
		return getDao().queryExam(param);
	}
	/**
	 * 根据examid查询出已经安排了考试的学员信息和所属于的班级
	 */
	public Map<String,Object> queryOpendStuds(Map<String,Object> param){
		return getDao().queryOpendStuds(param);
	}
	/**
	 * 根据班级开通
	 */
	public Map<String,Object> saveFromCls(Map<String,Object> param){
		return getDao().saveFromCls(param);
	}
	/**
	 * 修改学生信息,主要指修改禁止考试或是允许考试
	 */
	public Map<String,Object> updateState(Map<String,Object> param){
		return getDao().updateState(param);
	}
	/**
	 * 根据用户选择的记录一次删除多行
	 */
	public Map<String,Object> deleteStuds(Map<String,Object> param){
		return getDao().deleteStuds(param);
	}
}
