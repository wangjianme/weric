package com.topsen.financial.util.support.dao.page;

import java.util.HashMap;
import java.util.Map;

public class PageDBFactory {
	private static Map<String,PageDB> dbMap = new HashMap<String,PageDB>();
	public static String className;
	
	public static PageDB createNewDB() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		return (PageDB)Class.forName(className).newInstance();
	}
	
	public static void putDB(String name,PageDB pageDB){
		dbMap.put(name, pageDB);
	}
	
	public static PageDB getPageDB(String dbname){
		PageDB pageDB = dbMap.get(dbname);
		if (pageDB == null){
			try {
				pageDB = createNewDB();
				dbMap.put(dbname, pageDB);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return pageDB;
	}
	
	
	
	public static void releaseDB(String name){
		dbMap.remove(name);
	}
	public static void releasDB(){
		dbMap.clear();
	}
}
