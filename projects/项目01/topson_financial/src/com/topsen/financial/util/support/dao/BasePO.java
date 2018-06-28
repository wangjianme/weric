package com.topsen.financial.util.support.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BasePO {
	
	public String toString(){
		StringBuffer fieldValues = new StringBuffer();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields){
			String fieldName = field.getName();
			try {
				String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
				Method method = clazz.getMethod(methodName);
				Object fieldValue =method.invoke(this);
				field.setAccessible(true);
				fieldValues.append("["+fieldName+":"+fieldValue+",]");
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return fieldValues.toString();
	}
}
