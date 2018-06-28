package com.echarts.utils;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 用来读取配置文件
 * @author dashuju
 *
 */
public class PropUtils {
	
	private static Properties prop=null;
	private PropUtils(){		
	}
	static{
		try {
			prop=new Properties();
			prop.load(new FileInputStream(PropUtils.class.getClassLoader().getResource("conf.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	
	}
	public static String getProp(String key){
		return prop.getProperty(key);
		}
	public static Properties getProp(){
		return prop;
	}

}
