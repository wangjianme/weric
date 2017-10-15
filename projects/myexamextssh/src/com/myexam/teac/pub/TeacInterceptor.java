package com.myexam.teac.pub;
import java.util.Map;
import com.myexam.teac.login.action.LoginAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 验证教师是否登录
 * @author wangjianme
 * @version 1.0,2010-12-6
 */
@SuppressWarnings("serial")
public class TeacInterceptor extends AbstractInterceptor {
	public String intercept(ActionInvocation inc) throws Exception {
		if (inc.getAction() instanceof LoginAction ){
			String method = inc.getProxy().getMethod();
			System.err.println("Method为:"+method);
			if(method.equals("loginSuccess")){
				Map<String, Object> session = ActionContext.getContext().getSession();
				if (session.get("teac") == null) {
					System.err.println("TeacLogin....");
					return "teacLogin";
				} else {
					return inc.invoke();
				}
			}else{
				return inc.invoke();
			}
		}else{
			Map<String, Object> session = ActionContext.getContext().getSession();
			if (session.get("teac") == null) {
				System.err.println("TeacLogin....");
				return "teacLogin";
			} else {
				return inc.invoke();
			}
		}
	}
}
//@SuppressWarnings("serial")
//public class TeacInterceptor extends MethodFilterInterceptor{
//	public String doIntercept(ActionInvocation inc) throws Exception {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		if (session.get("teac") == null) {
//			System.err.println("TeacLogin....");
//			return "teacLogin";
//		} else {
//			return inc.invoke();
//		}
//	}
//}
