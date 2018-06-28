package spider.util;

import java.util.Set;
import java.util.TreeSet;

import spider.bean.KeyBean;



public class WebUtil {

	public static Set<KeyBean> showTopN(Set<KeyBean> set,int count){
		int innerCount = 0;
		Set<KeyBean> topNSet = new TreeSet<KeyBean>();
		for (KeyBean bean : set){
			topNSet.add(bean);
			innerCount++;
			if (innerCount == count){
				break;
			}
		}
		return topNSet;
	}
}
