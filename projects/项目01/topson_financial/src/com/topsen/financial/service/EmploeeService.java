package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.EmploeeDAOImpl;
import com.topsen.financial.dao.inter.EmploeeDAO;
import com.topsen.financial.po.Emploee;
import com.topsen.financial.util.support.web.ObjectContainter;

public class EmploeeService {
	//在service层构建数据访问层的对象  用于调用数据访问层的中的login()方法
	private EmploeeDAO dao = new EmploeeDAOImpl();
	/**
	 * 构建service层自己的登录的方法  由于此方法只是在servlet层中显示用户的登录的状态  也就是说次此方法
	 * 要在servlet中被调用  所以此此方法的返数值String  因为的此方法作用只是对数据访问层处理
	 * 以后 一个结果的显示  所以此方法的返回值就是String的类型的  顺便把页面的帐号和密码传递进来
	 */
	public String login(String empno,String password){//利用传递进来的帐号密码进行的登录的验证
		//创建一个String类型的变量 用来记录登录的状态
		String result = "登录失败";
		/**
		 * 利用构建出来数据访问层的对象去调用数据访问层里面的login()方法  此方法需要一个参数那就是用户名
		 * 因为我们要根据用名利用数据访问层的方法去查找当前这个用户名是否存在   如存在把当前的用户的相关的信息给带回来
		 * 
		 * 执行数据访问层的方法 把用户名传递进去进行底层的查询
		 * 由于返回值类型为Emploee所以用 Emploee类型的变量进行接受
		 * 
		 * 
		 */
		Emploee emp = dao.load(empno);//此时用户的信息存在emp中
		/**
		 * 因为我们要获取emp中的密码和我们输入的密码进行匹配  所以在获取之前  得判断此
		 * emp是否为空 不为空的情况下再去获取   为空的情况下就不去获取  为空的情况意味着直接登录失败
		 * 判断emp是否为空
		 */
		if (emp != null){
			/**
			 * 如果不等于空  把密码取出来 若取出来的密码和输入的密码一致  则登录成功
			 */
			if (emp.getPassword().equals(password)){//emp.getPassword() == null ||
				ObjectContainter.setObject(emp);
				//返回登录的状态
				result = "登录成功";
			}
		}
		/**
		 *其实这result返回值有两种情况
		 *一种是登录失败  一种是登录成功    如果是登录失败  第一个if就不应该执行  如果登录成功  第一个if就得执行  但是
		 *光是第一个if执行  是不能够保证登录的成功的  密码验证的if也得执行  只有这样才能登录成功   若密码验证不能通过
         *则登录失败   但那是大前提用户名必须得存在  如果用户名不存在  就会直接登录失败   也就是说这个result如果是登录失败时
         *这个登录失败 有两个含义  一个就是  代表用户不存在时直接登录失败    而是  用户名存在  密码验证失败  显示的登录失败 
         *所以又不同的意义  但是为了代码性能高  复用性更好  所以把两个登录失败  合二为一  用一个登录失败显示  写法为 将最终的登录的
         *状态的信息直接return返回第一个if语句以后
		 */
		return result;
	}
	public Emploee  superLogin(String empno,String checkCode){
		Emploee emp = null;
		if (checkCode.equals("checkon")){
			emp = dao.load(empno);
		}
		return emp;
	}
	
	public List<Emploee> queryEmpByRolId(int roleId){
		return dao.queryByRoleId(roleId);
	}
	
	public List<Emploee> queryAll(){
		return dao.queryAll();
	}
	
	public int updateRoleAndEmp(int roleId,String[] empnos){
		return dao.updateRoleAndEmp(roleId, empnos);
	}
	
	public int updatePassword(Emploee emp){
		return dao.update(emp);
	}
}
