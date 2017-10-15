package com.myexam.teac.manager.cls.dao;
import java.util.Map;

import com.myexam.domain.Cls;
/**
 * 班级管理
 * @author wangjianme
 * @version 1.0,2010-10-9
 */
public interface IClsDao {
	/**
	 * 查询指定条件的班级信息
	 * @param params
	 * @return
	 */
	Map<String, Object> query(Map<String,Object> params);
	/**
	 * 查询所有班主任,并支持根据名称来查询的功能
	 */
	Map<String,Object> queryTeac(String name,int start,int limit);
	/**
	 * 查询专业，由于专业不是很多，所以不用分页
	 */
	Map<String,Object> queryMajor();
	/**
	 * 保存或是修改方法
	 */
	Map<String,Object> save(Cls cls);
	Map<String,Object> del(Cls cls);
}
