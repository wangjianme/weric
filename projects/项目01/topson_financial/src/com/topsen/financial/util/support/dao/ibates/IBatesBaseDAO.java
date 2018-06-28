package com.topsen.financial.util.support.dao.ibates;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.topsen.financial.config.SqlMap;
/**
 * 
 * @author Administrator
 * 创建一个IBatesBaseDAO类  用来创建SqlMapClient map对象  然后把SqlMapClient map放在
 * 一个公有的方法中  外部通过调用个公有的方法来获取这个这个SqlMapClient map对象map
 */
public abstract class IBatesBaseDAO{
	//创建一个SqlMapClient map对象
	private SqlMapClient map;
	//通过公有的getMap()方法来获取我们的SqlMapClient map对象
	public SqlMapClient getMap() {
		return map;
	}
   //通过构造方法为SqlMapClient map构建对象
	public IBatesBaseDAO(){
		//通过
		this.map = SqlMap.getSqlMapInstance();
	}
}
