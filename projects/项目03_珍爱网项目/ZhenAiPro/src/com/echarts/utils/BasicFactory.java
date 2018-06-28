package com.echarts.utils;


public class BasicFactory {
	//˽������
	private static BasicFactory factory=new BasicFactory();
	private BasicFactory(){	
	}
	//���з���
	public static BasicFactory getFactory(){
		return factory;
	}
	//��ȡ�����
	public <T> T getInstance(Class<T> clazz){
		try {
			//��ȡ��ļ����֣�����������������
			String infName=clazz.getSimpleName();
			//��ȡʵ�����ȫ·����
			String fullName=PropUtils.getProp(infName);
			//(T) Class.forName(fullName)���䷽����ȡ��
			return (T) Class.forName(fullName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

}
