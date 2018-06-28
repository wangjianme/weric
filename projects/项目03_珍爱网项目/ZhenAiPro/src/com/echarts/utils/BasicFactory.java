package com.echarts.utils;


public class BasicFactory {
	//私有属性
	private static BasicFactory factory=new BasicFactory();
	private BasicFactory(){	
	}
	//公有方法
	public static BasicFactory getFactory(){
		return factory;
	}
	//获取类对象
	public <T> T getInstance(Class<T> clazz){
		try {
			//获取类的简单名字（不含包名的类名）
			String infName=clazz.getSimpleName();
			//获取实现类的全路径名
			String fullName=PropUtils.getProp(infName);
			//(T) Class.forName(fullName)反射方法获取类
			return (T) Class.forName(fullName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

}
