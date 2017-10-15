package com.myexam.teac.init.dept.service;

import java.util.List;
import java.util.Map;

import com.myexam.domain.Dept;

public interface IDeptService {
	List<Dept> query(String id);
	Map<String, Object> save(Dept dept);
	Map<String,Object> del(String deptId);
}
