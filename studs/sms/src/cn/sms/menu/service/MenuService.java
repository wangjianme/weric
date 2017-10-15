package cn.sms.menu.service;

import java.util.List;

import cn.sms.domain.Menu;
import cn.sms.menu.dao.MenuDao;

public class MenuService {
	private MenuDao dao = new MenuDao();

	public List<Menu> query(String userId, String parentId) {
		return dao.query(userId, parentId);
	}

	public List<Menu> query(String parentId) {
		return dao.query(parentId);
	}
}
