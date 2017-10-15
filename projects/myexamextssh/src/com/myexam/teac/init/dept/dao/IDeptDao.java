package com.myexam.teac.init.dept.dao;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Dept;

public interface IDeptDao {
	/**
	 * 查询全部
	 * @return
	 */
	List<Dept> query(String id);
	/**
	 * 保存
	 */
	Map<String,Object> save(Dept dept);
	/**
	 * 删除
	 */
	Map<String,Object> del(String deptId);
}
