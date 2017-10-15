package com.myexam.pub;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 转换
 * @author wangjianme
 * @version 1.0,2010-10-5
 */
public class JSONUtil {
	/**
	 * 将List<Map<String,Object>>转字符串
	 */
	public static String toJsonString(List<Map<String,Object>> coll){
		JSONArray json =JSONArray.fromObject(coll);
		String jsonStr = json.toString();
		return jsonStr;
	}
	/**
	 * 将Map<String,Object>转成字符串
	 */
	public static String toJsonString(Map<String,Object> map){
		JSONObject json = JSONObject.fromObject(map);
		String jsonStr = json.toString();
		return jsonStr;
	}
	/**
	 * 将JavaBean转成字符串
	 */
	public static String toJsonString(Serializable obj){
		JSONObject json = JSONObject.fromObject(obj);
		String jsonStr = json.toString();
		return jsonStr;
	}
	/**
	 * 将字符串转成List<Map<String,Object>>对像
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getListMap(String str){
		JSONArray json = JSONArray.fromObject(str);
		List<Map<String,Object>> list = (List<Map<String,Object>>)JSONArray.toCollection(json,Map.class);
		return list;
	}
	/**
	 * 将字符串转成Map<String,Object>对像
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getMap(String str){
		JSONObject json = JSONObject.fromObject(str);
		Map<String,Object> map = (Map<String,Object>)JSONObject.toBean(json,Map.class);
		return map;
	}
}
