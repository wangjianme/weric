package spider.filter;

import java.util.ArrayList;
import java.util.List;

import spider.bean.InfoContainer;
import spider.node.NodeFilter;

public class KeyWordFilter extends NodeFilter{
	private List<String> expWordList = new ArrayList<String>();
	{
		expWordList.add("nbsp");
		expWordList.add("&nbsp");
		expWordList.add("ui");
		expWordList.add("pc");
	}
	public String process(String key,String message,InfoContainer container) {
		if (!expWordList.contains(message)){
			return message;
		}
		return null;
	}

}
