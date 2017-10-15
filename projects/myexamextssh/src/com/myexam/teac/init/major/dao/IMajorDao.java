package com.myexam.teac.init.major.dao;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Major;

/**
 * 专业
 * @author wangjianme
 * @version 1.0,2010-10-6
 */
public interface IMajorDao {
	List<Map<String,Object>> query(Major major);
	Map<String,Object> save(Major major);
	Map<String,Object> del(String majorId);
	Map<String,Object> update(Major major);
}
