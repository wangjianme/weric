package com.myexam.teac.manager.stud.dao;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Stud;

/**
 * 学生管理
 * @author wangjianme
 * @version 1.0,2010-10-13
 */
public interface IStudManagerDao {
	/**
	 * 查询
	 * @param stud
	 * @return
	 */
	Map<String,Object> query(Map<String,Object> param);
	/**
	 * 删除，其实就是设置一下标示位
	 * @param param
	 * @return
	 */
	Map<String,Object> del(Map<String,Object> param);
	/**
	 * 查询班级,分页
	 */
	Map<String,Object> queryCls(Map<String,Object> param);
	/**
	 * 修改,目前对于一个学生，只能修状态、学号、和班级，其他信息由学生自己完成
	 */
	Map<String,Object> update(Map<String,Object> param);
}
