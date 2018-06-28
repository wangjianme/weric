package com.topsen.financial.util.support.web;

public class ObjectContainter {
	private static final ThreadLocal<Object> objectContainter = new ThreadLocal<Object>();
	//�洢ֵ
	public static void setObject(Object value){
		Object currentObject = objectContainter.get();
		if (currentObject == null){
			objectContainter.set(value);
		}
	}
	//Ĭ���Ƴ�ģʽ��ȡֵ
	public static Object getObject(){
		return getObject(true);//currentObject-->�������еĶ���
	}
	//�Ƴ�ģʽ��ȡֵ
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
