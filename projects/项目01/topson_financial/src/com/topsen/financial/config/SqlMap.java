package com.topsen.financial.config;

import java.io.Reader;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * 
 *创建一个SqlMap类   用来给SqlMapClient map构建对象用
 */
public class SqlMap {
	//声明一个SqlMapClient map对象
	private static SqlMapClient map;
	//创建一个静态块  
	static {
		try {        
			/**
			 * 声明dbconfig.xml文件
			 * 此时我们有一个问题 那就是要想对数据库底层进行访问  必须有对数据库进行增删改查的方法  这样的方法
			 * 没有是无法对数据库进行操作的  但是不要紧  这些方法是存在的  是框架给我们提供的  我们可以直接用的
			 * 但是有一点必须注意  那就是当我们调用方法对数据库进行操作时  这个方法必须连接上数据库   否则无法对数据库
			 * 增删改查   之前的方法都是我们自己写的我们可以控制连接数据库的代码  但是现在的方法都是框架提供好的
			 * 我们无法确定里面有没有连接上数据的代码  其实理论上是这样的  当我们调用这个方法时  被调用的方法就要
			 * 连接上数据库  虽然我们现在不能控制方法里面能有连接数据库的代码  但是我们可以调用这个方法的对象
			 * 具备连接数据库的实力   这样的情况下  当我们利用map对象调用方法时就会连接上数据库  因为此时这个map
			 * 对象具备了连接数据库的实力   然后按照相应的顺序把我们的sql语句进行执行
			 * 但是我们得分析这个map对象是如何具备连接数据库的实力的  其实很简单的  原理是这样的  那就是
			 * 我们会把连接数据库的相关的代码  比如帐号  密码   url通过框架标签的形式写在一个配置文件中  
			 * 代码如下
			 * String resource = "com/topsen/financial/config/dbconfig.xml";
			 * 然后通过 String reader = Resouraces.getResourceAsreader(resource)读取出来
			 * 并且赋值给reader此时reader里面保存的就是连接数据库相关的代码或者说是配置信息
			 * 接下来我们还要利用Resouraces.getResouraceAsReader(resourace)构建出来最终的对象
			 * 综上所述我们的连接数据库的前提就是读取的这个配置文件  所有的连接数据里面的所有的资源全部都在这里
			 * 通过方法获取在通过方法加工  构建出来的最终的对象   所以以上的两个方法就是分别是读取配置文件和构建
			 * 最终的对象的
			 * 我们知道这个配置文件中包含了连接数据库的资源比如帐号密码  如果我们把连接数据库的帐号  密码 url写死 那么只能连接
			 * 某一固定的数据库了  想要连接别的也可以 但是必须得更改连接数据库的状态参数代码   比如修改
			 * 即将要连接数据库的帐号密码  url   但是这样做是绝对不可以的   因为之前我们讲过项目做完以后代码能改则不改
			 * 因为盲目的改动会造成程序的整体的瘫痪   所以这样的做法根本不可取的  连接数据库的核心就是帐号密码url
			 * 只要连接的这些元素都在   数据库就能连接上  所以说甭连接数据库的参数代码写在哪里  能连接上即可  但是为了维护
			 * 程序的完整性  直接写在配置文件中是不可以的   所以我们只能把当前连接数据库的相关的代码写在一个propertirs
			 * 的配置文件中  然后 采取办法引用即可  其实导入.properties文件所在的路径 我们直接就能够引用  
			 * 一切写好以后 当写上连接oracle数据库时  连接的就是oracle数据库  也就是写上什么样的数据库连接状态参数
			 * 连接就是哪个数据库   核心就是我们连接数据库的核心元素写在一个.properties文件中  然后直接引用路径访问即可
			 * 我们虽然更改了代码  但是 是在配置文件中更改了代码  并没有在Java代码中直接修改代码  所以不会对象对程序造成任何的破坏
			 * 这样即维护了程序的稳定性  又实现了程序的拓展性  以后一定要注意这一点的应用  
			 * 
			 */
			String resource = "com/topsen/financial/config/dbconfig.xml";
			/*
			 * 通过Resources类调用getResouraceAsReader("配置文件所在的路径")来读取我们提供的配置的文件
			 * 此方法执行完以后相当于把配置文件里面的内容保存到Reader reader中
			 */
			Reader reader = Resources.getResourceAsReader(resource);
			/**
			 * 通过框架给我们提供的SqlMapClientBuilder类来调用buildSqlMapClient("配置文件的资源")
			 * 方法构建对数据库增删改查的对象   
			 * 我们可以这样想  我们通过map来调用数据底层的增删改查的方法  说明这个方法肯定会和数据库底层的增删改查的
			 * 方法有关   要不然不能用它去调用   我们可以这样理解对数据库增删改查的方法全部都存在dbconfig.xml
			 * 文件中  通过Resouraces.getResouraceAsReader()来构建最终的数据库底层的增删改查的方法
			 * 换句话说以下的代码就是为了搭建增删改查和map之间的关系只有这样 map对象最终去调用相关操作的方法
			 * map = SqlMapClientBuilder.buildSqlMapClient(reader);
			 */
			map = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//无参数的构造方法以备后用
	public SqlMap(){

	}
	/*
	 * 此方法将我们获取的对数据库增删改查的对象进行返回 
	 * 经过以上的分析我们可以发现 此类只是用来生产这个map对象  是真正构建这个对象的 所以 这个map对象生产完以后  我们是要使用这个map对象的
	 * 所以为了使用很方便  所以我们可以单独创建一个类  在这个类中我们声明一个SqlMapClient map  我们在这里只是声明一个SqlMapClient map
	 * 但是对象光声明没有用的  还得做具体的构建   正常我们构建一个对象  都是通过new来实现的   但是现在我们不能通过
	 * new来构建了  因为此时构建不了   无论通过哪一种的方式  目地都是为了创建一个对象    由于我们把具体单位对象已经给创建好了
	 * 所以在构建对象时只需要调用这个对象所在的方法即可
	 * 然后把这个map对象存入到这个类中  
	 * 
	 */
	public static SqlMapClient getSqlMapInstance(){
		System.out.println("SqlMap="+map);
		//调用数据库增删改查的方法的最终的对象
		return map;
	}


}
