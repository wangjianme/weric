package cn.grades.menu.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Menu;

public interface IMenuService {

	List<Menu> query(String uid, String parent);
	List<Menu> query(String parentId);
	public Map<String, List<String>> menuUser();
}