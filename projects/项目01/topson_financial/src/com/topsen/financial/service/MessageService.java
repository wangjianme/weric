package com.topsen.financial.service;

import com.topsen.financial.dao.impl.MessageDAOImpl;
import com.topsen.financial.dao.inter.MessageDAO;
import com.topsen.financial.po.BackMessage;

public class MessageService {
	private MessageDAO dao = new MessageDAOImpl();
	
	public void insert(BackMessage message){
		BackMessage backMessage = dao.load(message.getRebId());
		if (backMessage == null){
			dao.insert(message);
		}else{
			dao.update(message);
		}
		
	}
	
	public BackMessage query(String rebId){
		return dao.load(rebId);
	}
}
