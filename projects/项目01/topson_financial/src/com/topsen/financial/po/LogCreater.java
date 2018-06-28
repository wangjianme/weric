package com.topsen.financial.po;


import com.opensymphony.xwork2.ActionContext;
import com.topsen.financial.dao.impl.LogDAOImpl;
import com.topsen.financial.dao.inter.LogDAO;

public class LogCreater {
	public static final String CREATE="create";
	public static final String TYPE_SYSTEM="ϵͳ��־";
	public static final String TYPE_OPERATION="����";
	public static final String TYPE_LOGIN="������־";
	public static final String TYPE_OVERPOWER="ԽȨ��־";
	
	public static void saveLog(OperationLog log){
		ActionContext.getContext().getValueStack().getContext().put(CREATE, log);
	}
	
	public static void saveLogToDB(Emploee emp){
		Object object = ActionContext.getContext().getValueStack().getContext().remove(CREATE);
		if (object != null && object instanceof OperationLog){
			OperationLog log = (OperationLog)object;
			log.setEmp(emp);
			log.setLogDetail(emp.getEname()+"��"+log.getYear()+"-"+log.getMonth()+"-"+log.getDay()+" "+log.getTime()+" ִ�в���:"+log.getContent()+" �������:"+log.getLogDetail());
			LogDAO dao = new LogDAOImpl();
			dao.insert(log);
		}
		
	}
}
