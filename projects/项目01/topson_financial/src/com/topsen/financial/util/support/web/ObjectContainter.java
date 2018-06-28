package com.topsen.financial.util.support.web;

public class ObjectContainter {
	private static final ThreadLocal<Object> objectContainter = new ThreadLocal<Object>();
	//存储值
	public static void setObject(Object value){
		Object currentObject = objectContainter.get();
		if (currentObject == null){
			objectContainter.set(value);
		}
	}
	//默认移除模式获取值
	public static Object getObject(){
		return getObject(true);//currentObject-->正在运行的对象
	}
	//移除模式获取值
	public static Object getObject(boolean clear){
		Object currentObject = objectContainter.get();
		if (clear){
			if (currentObject != null){
				objectContainter.set(null);
				objectContainter.remove();
			}
		}
		return currentObject;
	}
}
