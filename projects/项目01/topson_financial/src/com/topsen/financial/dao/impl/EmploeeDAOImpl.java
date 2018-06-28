package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.topsen.financial.dao.inter.EmploeeDAO;
import com.topsen.financial.po.Emploee;
/**
 * 
 * @author Administrator
 * 创建数据访问层的类
 */
public class EmploeeDAOImpl extends EmploeeDAO {
	/**
	 * 数据访问层中的登录的方法() 此方法参数列表为一个参数 就是用户名  通过此参数把用户名传递进来进行数据的底层的
	 * 访问
	 * 
	 */
	public Emploee load(String empno) {
		//测试用户名是否传递进来
		System.out.println("用户名(empno)="+empno);
		//创建一个Emploee用来接受用户名相关的信息
		Emploee emp = null;
		try {
			/**
			 * 将sql语句送入数据库里面执行  
			 * 这个queryForObject("emp_space.one",empno);方法就相当于我们以前封装的对数据库
			 * 查询的方法  只不过这个方法是框架给我们提供好的  拿过来直接用即可  但要注意这个调用这个方法的对象
			 * 必须是SqlMapClient map这个类的对象  我们采取的措施就是把创建好这个对象并且设置为私有的
			 * 然后把这个私有的SqlMapClient map对象放在返回值为qlMapClient map公有的方法中即可 然后在
			 * 外部直接调用 这个私有的SqlMapClient map所在的带有返回值类型为SqlMapClient map的共有的
			 * 方法中即可   方法执行完毕以后会把我们SqlMapClient map的这个对象给返回
			 * 
			 * (Emploee) this.getMap().queryForObject("emp_space.one",empno);这个方法主要是
			 * 根据传递过来的帐号进行查询  也就是说把我们查询过来的帐号当作sql语句查询的条件  
			 * 若能查回结果说明我们的帐号是存在的  把我们的结果保存起来    
			 * 正常情况下我们的sql语句是写在queryForObject("emp_space.one",empno);方法内部的
			 * 但是此时的这个方法是框架提供好的 所以这个sql语句我们无法人为地给写在内部   所以只能写在外部
			 * 也就是说既然调用这个方法  这个方法能帮助我们完成增删改查的操纵  说明这里面是一定有sql语句  但是现在
			 * 我们人为的控制不了   所以sql语句只能通过 此方法以参数的形式传递进去  此方法此时有两个参数  一个
			 * 帐号 另一个就是查询方法做执行需要的查询的sql语句  其实以下emp_space.one这个东西代表的就是sql语句
			 * 而且是我们要查询的sql语句   这个emp_space.one也是相当于存在这个map对象中的 因为这个map对象
			 * 的原材料是来自于配置文件dbconfig.xml中  这个配置文件中包含了数据库中  用的所有的东西  比如连接数据库
			 * 的代码   具体的操作的sql语句 只不过这里面的sql语句没有直接写在此配置文件中  而是写在了一个emploee_sqlmap.xml
			 * 文件中  由于当前我们操作的就是对用户进行操作  看用户是否存在  也就是对emplooe进行操作 所以
		     * 我们但单独建立一个emploee_sqlmap.xml文件  把所有的对empolee操作的相关的sql语句写在这个
		     * 配置文件中
		     * 
		     * 
		     * 执行查询的操作  把查询的结果  用类Emploee的对象去接收查询出来的该用户的信息
		     *
		     *
		     * 注意方法的返回值类型一定是Object类型  因为这个方法是 底层给提供好的    大众都可以使用   不能
		     * 局限于某一种数据类型  所以最终我们一定要注意数据类型的转换  转换成我们想要的数据类型
			 */
			emp = (Emploee) this.getMap().queryForObject("emp_space.one",empno);
			System.out.println("emp="+emp);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//将用户最终的信息给返回
		return emp;
	}

	public List<Emploee> queryByRoleId(int roleId) {
		List<Emploee> list = null;
		try {
			list = this.getMap().queryForList("emp_space.queryByRoleId",roleId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Emploee> queryAll() {
		List<Emploee> list = null;
		try {
			list = this.getMap().queryForList("emp_space.queryAll");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int updateRoleAndEmp(int roleId, String[] empnos) {
		int i = 0;
		try {
			this.getMap().startTransaction();
			this.getMap().startBatch();
			this.getMap().delete("emp_space.delete", roleId);
			if (empnos != null){
				for (String empno : empnos){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("empno", empno);
					map.put("roleId", roleId);
					this.getMap().insert("emp_space.insert", map);
				}
			}
			this.getMap().executeBatch();
			this.getMap().commitTransaction();
			i = 1;
		} catch (SQLException e) {
			try {
				this.getMap().getCurrentConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				this.getMap().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;

	}

	public int update(Emploee t) {
		int i = 0;
		try {
			i = this.getMap().update("emp_space.update",t);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
//	public static void main(String[] args) {
//     byte a = 1;
//     byte b = 127;
//     System.out.println((byte)(a+b)); 128  -128
//	}
}
