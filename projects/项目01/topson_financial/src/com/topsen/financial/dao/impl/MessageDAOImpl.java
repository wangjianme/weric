package com.topsen.financial.dao.impl;

import java.sql.SQLException;

import com.topsen.financial.dao.inter.MessageDAO;
import com.topsen.financial.po.BackMessage;

public class MessageDAOImpl extends MessageDAO{

	@Override
	public BackMessage load(String rebId) {
		BackMessage message = null;
		try {
			message = (BackMessage) this.getMap().queryForObject("message_space.one",rebId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}

	public int insert(BackMessage t) {
		int i = 0;
		try {
			this.getMap().insert("message_space.insert",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;
	}

	public int update(BackMessage t) {
		int i = 0;
		try {
			this.getMap().update("message_space.update",t);
			i = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;
	}

}
