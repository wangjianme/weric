package com.myexam.teac.menu.service;

import java.util.List;
import java.util.Map;

public interface ITeacMenuService {
	List<Map<String, Object>> query(String parent,String uid);
}
