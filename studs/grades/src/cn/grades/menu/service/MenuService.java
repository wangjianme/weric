package cn.grades.menu.service;

import cn.grades.domain.Menu;
import java.util.List;
import java.util.Map;

import cn.grades.menu.dao.IMenuDao;
import cn.grades.menu.dao.MenuDaoJdbc;

public class MenuService implements IMenuService {
	private IMenuDao dao = new MenuDaoJdbc();
	public List<Menu> query(String uid,String parent){
		return dao.query(uid, parent);
	}
	public List<Menu> query(String parentId) {
		return dao.query(parentId);
	}
	public Map<String, List<String>> menuUser(){
		return dao.menuUser();
	}
}
