package spider.filter;

import spider.bean.InfoContainer;
import spider.node.NodeFilter;

public class SplitFilter extends NodeFilter{


	@Override
	public String process(String key,String message,InfoContainer container) {
		if (message.contains("</a>")){
			message = message.replace("</a>", "");
		}
		return message;
	}

}
