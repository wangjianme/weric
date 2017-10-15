package cn.sms.cls.service;

import java.util.Map;

import cn.sms.cls.dao.ClsDao;
import cn.sms.domain.Cls;

public class ClsService {
	private ClsDao dao = new ClsDao();
	public Map<String, Object> query(){
		return dao.query();
	}
	public Cls save(Cls cls){
		return dao.save(cls);
	}
}
