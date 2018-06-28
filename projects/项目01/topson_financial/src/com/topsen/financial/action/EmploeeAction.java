package com.topsen.financial.action;


import java.util.List;

import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.LogCreater;
import com.topsen.financial.po.OperationLog;
import com.topsen.financial.po.Role;
import com.topsen.financial.service.EmploeeService;
import com.topsen.financial.service.RoleService;
import com.topsen.financial.util.support.struts2.BaseAction;
import com.topsen.financial.util.support.web.ObjectContainter;

public class EmploeeAction extends BaseAction{
	private EmploeeService service = new EmploeeService();
	private RoleService roleService = new RoleService();
	private String empno;
	private String password;
	private String[] empnos;
	private String operation;
	private List<Emploee> empList;
	private List<Emploee> allList;
	private String result;
	private Role role;
	private List<Role> roleList;
	
	public String getResult() {
		return result;
	}

	public void setEmpnos(String[] empnos) {
		this.empnos = empnos;
	}
	
	public Role getRole() {
		return role;
	}
	
	public List<Emploee> getEmpList() {
		return empList;
	}
	
	public List<Emploee> getAllList() {
		return allList;
	}
	
	public String getOperation() {
		return operation;
	}
	

	public List<Role> getRoleList() {
		return roleList;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	 * 
	 */
	public String updatePassword(){
		String password = this.getRequest().getParameter("password");
		Emploee emp = (Emploee)getSession().getAttribute("emp");
		if (password == null || password.equals("")){
			emp = new Emploee();
			emp.setEmpno(empno);
			password="000000";
		}
		emp.setPassword(password);
		service.updatePassword(emp);
		return "upconfirm";
	}
	/**
	 * 此方法为登录的方法 加验证码的意义就是为了验证登录地状态 也就是登录成功或者说是登录失败  也就是说你想登录可以但是
	 * 必须验证码验证得通过  换句话说因为登录受验证的限制只有验证码验成功了 即我们输入的验证码和随机产生的验证码必须验证成功  只有
	 * 在这样的前提下我们才可以登录 也就是说 验证控制着登录  只有验证通过了才可以登录  在说直白点 只有 
	 * 登录的代码的写在验证码验证成功的里面
	 */
	public String login(){		
		//String checkCode = (String)getSession().getAttribute("check_code");
		//String requestCode = getRequest().getParameter("checkcode");
		//获取产生的验证码
		String checkCode = (String)getSession().getAttribute("certCode");
		//将产生的验证码进行输出
		System.out.println("checkCode:"+checkCode);
		//获取输入的密码
		String requestCode = getRequest().getParameter("yz");
		//将获取输入到的验证码进行打印输出
		System.out.println("requestCode:"+requestCode); 
		//判断我们输入的验证码和随机产生的验证码是否一致  若一致可以进行正常的登录  若不一致提示验证码验证失败
		if (requestCode == null||checkCode==null||!checkCode.equals(requestCode)){
			//不一致显示验证码验证错误
			result = "验证码错误";
			//result = "登录成功";
			//如果验证成功则进行正常的的登录的验证
		}else{
			/**
			 * 验证成功的前提下开始登录验证 
			 * 此页面就相当于一个servlet  根据我们的经验可以知道 在servlet里面构建services层的对象然后调用里面的
			 * 方法 接着在在调用service方法里面构建dao层的对象  然后利用构建出来的对象调用dao里面的方法
			 * 调用dao里面的方法就要进行最后的的登录的验证了   所以此时的dao登录的方法里面就得参数  而且是两个参数  一个是帐号
			 * 一个是密码  根据传递进来的帐号进行查询看此账号是否存在  若存在把此账号里面的密码取出来  和此时我们的输入的密码进行匹配  若匹配
			 * 成功则返回登录成功的提示消息 若登录失败则返回的登录失败的提示消息  由于此时的dao层中的登录地方法是在service层登录中的方法调用地
			 * 所以此时dao层的方法返回的登录的状态的提示会停留在services层中的登录的方法中  而且调用这个方法的对象是在servlet中创建的
			 * 所以我们要在servlet层调用这个services方法  目地就是把的登录的状态给返回来servlet层也就是业务层  所以service层里面的方法
			 * 最好是String  因为我们要在业务层将登录的状态给返回  然后根据返回的状态判断最终的登录成功还是登录失败  其实我们落下一个问题
			 * 那就是dao层里面的登录的方法 两个参数谁传递进来的  要想知道这两个参数谁传递进来的  必须知道这两个参数是干什么用的  比如dao登录方法的两个
			 * 参数就是代表用户的输入的帐号和密码  所以这两个参数就是由用户传递进来的  根据我们经验可知 用户传递
			 * 进来的参数我们可以在servlet层获取 由此可见这个参数肯定是从servlet层传递进来的  servlet所
			 * 所负责的工作就是取调跳   通过相关的方法把页面传递进来的值给取出来  然后传递到servlet中的service
			 * 层  在传递到service层中的dao的登录的方法中
			 * 
			 * 此方法的具体的解读：
			 *         把从页面的获取的数值传递到service层中的login方法  在service层中利用构建出来的dao
			 *         的对象去调用dao层中的方法  然后把参数传递到dao层的方法中   实现的数据库的相关的操作
			 *         根据我们所学过的面向对象可知 在程序执行的个过程中  如果遇到方法就会执行方法   方法执行完毕以后
			 *         再回到方法调用处按着程序执行顺序继续执行
			 */
			result = service.login(empno, password);
		    /**
		     * 把得到登录的状态进行比较  如果result里面存储的就是登录成功  那么就是登录成功
		     * 如果存储是登录岁失败  说明登录失败
		     * 
		     * 
		     * 验证通过显示登录成功
		     */
			if (result.equals("登录成功")){
				/**
				 * 由于我们做的是5张表的权限的登录   以前的登录是在登录成功后 直接跳到主页面进行功能的操作
				 * 但是现在的登录是在登录后根据用户名选择角色   再根据选择的角色去选择权限 说到这里5
				 * 张表的权限的登录目的就是给用户分配权限  
				 * 此时我们就会登录成功  登录成功后立刻记住此时登录的用户名  然后根据用户名去查找角色
				 * 
				 * 根据用户名去查找角色  这个用户名已经存在了   在找到一个方法 由这个方法和提供的用户名去查找角色
				 * 	这个roleList = roleService.queryEmpRole(empno);方法就是根据用户名查找角色
				 *  根据我们的经验可知  涉及到具体的查询的  是把数据传递到servlet层  在servlet层构建service
				 *  的对象   然后在service中构建数据访问层的对象  调用底层的方法执行相应的操作  那么此时
				 *  也是一样的   数值存在了  就得需要方法了  构建services的对象和dao的对象了   但是这个
				 *  services层的得存在 里面包含根据用户名查找角色的方法   那么对应的dao数据访问层也得存在
				 *  里面的方法也是根据用户名查找角色的方法   所以的当务之急得创建这service层和dao层
				 *  
				 *  
				 *  根据用户名去查找角色  一定要把用户名传递进去 只有这样其中的方法才能根据用户名去查找
				 *  否则的话是不能的  
				 *  
				 *  将查询到用户对应的角色保存到roleList集合中
				 */
				roleList = roleService.queryEmpRole(empno);
				//把此值先利用setAttribute()方法存起来  然后在权限的servlet层的利用getSession.getAttribute()把值取出来
				getSession().setAttribute("emp",ObjectContainter.getObject());
				getSession().setAttribute("empno", empno);
				LogCreater.saveLog(OperationLog.getNewLog(LogCreater.TYPE_SYSTEM, "emploee", "访问系统", "登录成功"));
				//getSession().setAttribute("operation", "role");
				//把角色赋值给operation  
				operation="role";
			
			
			}else{
				LogCreater.saveLog(OperationLog.getNewLog(LogCreater.TYPE_SYSTEM, "emploee", "访问系统", "登录失败"));
			}
		}
		//此时的跳转页面根据框架的包去找  根据包去找到对应的struts框架    
		return "next";
	}
	public String queryEmpByRoleId(){
		String roleId = this.getRequest().getParameter("roleId");
		empList = service.queryEmpByRolId(Integer.parseInt(roleId));
		allList = service.queryAll();
		role = roleService.queryOne(Integer.parseInt(roleId));
		return "go";
	}
	
	public String updateGroup(){
		String roleId = this.getRequest().getParameter("roleId");
		int i = service.updateRoleAndEmp(Integer.parseInt(roleId),empnos);
		return "update";
	}
}
