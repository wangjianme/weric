package cn.grades.menu.dao;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Menu;

public interface IMenuDao {

	List<Menu> query(String uid, String parent);

	/**
	 * 菜单查询
	 */

	List<Menu> query(String parentId);

	/**
	 * 查询哪一个菜单 ，哪些用户可以访问
	 */
	Map<String, List<String>> menuUser();

}