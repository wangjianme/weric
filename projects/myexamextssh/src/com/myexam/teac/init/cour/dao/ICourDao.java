package com.myexam.teac.init.cour.dao;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Cour;
import com.myexam.domain.Major;
/**
 * 课程管理
 * @author wangjianme
 * @version 1.0,2010-10-7
 */
public interface ICourDao {
	/**
	 * 查询课程
	 * @param cour
	 * @return
	 */
	Map<String,Object> query(Map<String,Object> param);
	/**
	 * 保存
	 */
	Map<String,Object> save(Map<String,Object> param);
	/**
	 * 专业帮助
	 */
	List<Major> queryMajor();
	Map<String,Object> del(Cour cour);
}
