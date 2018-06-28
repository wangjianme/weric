package com.topsen.financial.action;

import java.util.List;

import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.Power;
import com.topsen.financial.po.Role;
import com.topsen.financial.service.PowerService;
import com.topsen.financial.service.RoleService;
import com.topsen.financial.util.support.struts2.BaseAction;

public class PowerAction extends BaseAction {
	private PowerService service = new PowerService();
	private RoleService roleService = new RoleService();
	private List<Power> powerList;
	private List<Power> roleList;
	private Role role; 
	private int[] powerId;
	public void setPowerId(int[] powerId) {
		this.powerId = powerId;
	}
	public Role getRole() {  
		return role;
	}
	public List<Power> getPowerList() {
		return powerList;
	}
	public List<Power> getRoleList() {
		return roleList;
	}
	public String queryAllPower(){
		String roleId = this.getRequest().getParameter("roleId");
	
		powerList = service.queryAll();
		roleList = service.queryByRoleId(Integer.parseInt(roleId));
		role = roleService.queryOne(Integer.parseInt(roleId));
		return "next";
	}
	public String insertNewPower(){
		String roleId = this.getRequest().getParameter("roleId");
		service.updatePowerAndRow(powerId, Integer.parseInt(roleId));
		return "go";
	}
	/**
	 * 
	 * 此方法是根角色id选择角色  详细分析如下:
	 *    经过我们以前的分析可以知道  不同的人登录有不同的角色   不同的角色  有不同的权限   也就是说角色  是人的角色 
	 *    权限是人的权限   我们已经完成了根据用户去选则角色   现在我们要根据角色去选择权限    根据用户去选择角色时 我们
	 *    是通过在登录框输入帐号密码   点击登录到后台进行数据库的验证  如果登录成功  此时记录下的此用户名对应的帐号
	 *    然后调用根据用户名去查询角色的方法完成 角色的查询 这样的一个过程我们完成了  根据用户名查找角色    角色查询到了
	 *    把角色的信息  存储到绝色类型的集合中  然后把查询到的角色遍历以下拉列表的形式显示在页面上  然后供用户去选择角色
	 *    当用户在下拉列表中选择角色的时候  这个该用户对应的角色已经存在了   接下来要根据这个角色去选择权限了 此时根据
	 *    用户去选择权限的这个页面  在后台对应的代码页面为index.jsp页面  根据用户名去选择角色以后  立刻就要根据角色去选择
	 *    权限了  所以在index.jsp页面的form表单中  有这样的代码<form action="power!queryEmpPowerByRoleId.action" method="post">
	 *   当角色选择完以后要跳转到这个页面  去根据用户去选择角色了  这个页面就是权限的一个servlet页面  这个里面方法的
	 *   执行的过程   输入用户名去验证登录的时候后台代码的执行的过程是一样的  在这里我们做一个简短的分析   页面跳转以后会跳到
	 *   权限的sevlet页面  然后根据方法的见名知意图的原理  找到根据角色去查找权限的方法   方法如下
	 *    queryEmpPowerByRoleId() 根据此方法就可以完成根据角色去查找权限  记住一点这个方法没有的参数   
	 *    因为我们可以利用request把决赛we在当前的这个servlet中给取出来  然后在servlet层中构建service层
	 *    这个service层指的是PowerService这个service层  然后调用里面的根据角色去查找权限方法  此方法是一定
	 *    是需要带参数的   因为要把在sertvlet层取出来的角色id传递到根据去查找权限的方法中  在这个方法中在
	 *    构建权限数据访问层的方法   然后调用数据访问层的根据角色id查找权限的方法  然后方法的调用的原理
	 *    在程序的执行的过程中遇到方法执行方法  方法执行完毕以后再回到方法调用处继续执行   在数据
	 *    访问层的方法中主要根据传递的角色id去查找权限  查询的结果为权限  显然需要一个权限类型的集合来接收
	 *    查询出来的结果  在servlet层中在用权限类型的集合接受一下   这样方法执行的完毕后  就会把根据角色去查询权限结果然后把结果返回到第一次的
	 *    方法的调用处      然后根据结果进行的响应的处理   根据我们的经验可知  要想知道某个用户所对应的角色  显然查找的是用户的情况  因为查找的是
	 *    用户的角色  这个用户必须有对应的角色  所以显然查询的是用户表和角色表的中间表  查询的条件 就是当期登录的账户作为登录用户作为查找角色的条件
	 *    同样的道理   角色有了以后要选择权限   如果在程序执行以前就想猜到某个用户所对应的角色所对应的权限是谁  这个过程就相当于
	 *    根据角色去查询权限  如果是这样  那么这个角色就得有对应的权限  如果没有对应的权限  根本查询不出来  根据的这样的线索 角色表和权限表的中间表就是这样的表
	 *    sql语句查询这个表  以角色作为查询的条件 就可以查询出 角色所对应的权限   但是此时用户所查询的角色对应的权限不是唯一的 
	 *    这一关系可以在角色表和权限表的中间表中看出来    
	 *    
	 *    把页面的传递过来的值给取出来然后传递到services层  在service中构建权限的dao的对象 然后
	 *    利用dao对象来调用底层的数据访问的方法  最终把查询的权限的结果给显示出来即可
	 *
	 */
	public String queryEmpPowerByRoleId(){
	   /*
	    * 通过继承BaseAction 把其中的request 继承过来  然后利用方法
	    * getParameter("roleId")把角色给取出来
	    */
		String roleId = this.getRequest().getParameter("roleId");
		//测试roleId是否取到
		System.out.println("roleId="+roleId);
		/*
		 * 根据roleId去查询权限的方法  把获取到的roleId传递到queryUserPower()方法中  由于
		 * 此时的roleId但是由String类型的  queryUserPower()方法里面的参数是整型的
		 * 所以此时要根据Integer.parseInt(roleId)将其字符串的roleId转化为int类型的oleId
		 * 
		 * 方法执行完毕以后把用一个权限类型的集合来接受查询出来的权限的结果
		 */
		powerList = service.queryUserPower(Integer.parseInt(roleId));
		//
		Emploee emp = (Emploee)getSession().getAttribute("emp");
		
		emp.setPowerList(powerList);
		return "sucess";
	}
	
}
