package com.myexam.teac.init.edusys.dao;

import java.util.List;
import java.util.Map;

/**
 * 学制管理
 * @author wangjianme
 * @version 1.0,2010-10-14
 */
public interface IEdusysDao {
	/**
	 * 查询出所有学制
	 * @return
	 */
	Map<String,Object> query();
	Map<String,Object> save(List<Object> list);
	Map<String,Object> del(Object o);
}
