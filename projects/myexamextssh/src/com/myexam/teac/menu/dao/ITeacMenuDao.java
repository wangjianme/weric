package com.myexam.teac.menu.dao;

import java.util.List;
import java.util.Map;

public interface ITeacMenuDao {
	List<Map<String,Object>> query(String parent,String uid);
}
