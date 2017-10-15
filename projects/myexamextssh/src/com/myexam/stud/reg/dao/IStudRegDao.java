package com.myexam.stud.reg.dao;

import java.util.Map;

import com.myexam.domain.Cls;
import com.myexam.domain.Stud;

/**
 * 学生注册功能
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
public interface IStudRegDao {
	/**
	 * 所有学制
	 * @return
	 */
	Map<String,Object> queryEdusys();
	/**
	 * 所有专业
	 */
	Map<String,Object> queryMajor(String edusysId);
	/**
	 * 查询 出所有班级
	 */
	Map<String,Object> queryCls(Map<String,Object> param);
	Map<String,Object> save(Stud stud);
}
