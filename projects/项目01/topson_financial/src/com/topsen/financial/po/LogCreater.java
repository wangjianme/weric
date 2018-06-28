package com.topsen.financial.po;


import com.opensymphony.xwork2.ActionContext;
import com.topsen.financial.dao.impl.LogDAOImpl;
import com.topsen.financial.dao.inter.LogDAO;

public class LogCreater {
	public static final String CREATE="create";
	public static final String TYPE_SYSTEM="系统日志";
	public static final String TYPE_OPERATION="操作";
	public static final String TYPE_LOGIN="访问日志";
	public static final String TYPE_OVERPOWER="越权日志";
	
	public static void saveLog(OperationLog log){
		ActionContext.getContext().getValueStack().getContext().put(CREATE, log);
	}
	
	public static void saveLogToDB(Emploee emp){
		Object object = ActionContext.getContext().getValueStack().getContext().remove(CREATE);
		if (object != null && object instanceof OperationLog){
			OperationLog log = (OperationLog)object;
			log.setEmp(emp);
			log.setLogDetail(emp.getEname()+"于"+log.getYear()+"-"+log.getMonth()+"-"+log.getDay()+" "+log.getTime()+" 执行操作:"+log.getContent()+" 操作结果:"+log.getLogDetail());
			LogDAO dao = new LogDAOImpl();
			dao.insert(log);
		}
		
	}
}
