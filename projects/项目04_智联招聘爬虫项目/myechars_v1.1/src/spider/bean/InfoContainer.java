package spider.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoContainer {
	private Map<String,List<InfoBean>> beanMap = new HashMap<String,List<InfoBean>>();

	public Map<String,List<InfoBean>> getMap() {
		return beanMap;
	}
	
	public boolean contains(String key,String value){
		List<InfoBean> list = beanMap.get(key);
		if (list == null){
			return false;
		}
		return list.contains(new InfoBean(key,value));
	}
	
	public void addInfo(String key,InfoBean bean){
		List<InfoBean> list = beanMap.get(key);
		if (list == null){
			list = new ArrayList<InfoBean>();
		}
		list.add(bean);
		beanMap.put(key, list);
	}
	
	public String toString(){
		return this.beanMap.toString();
	}
	
}
