package spider.filter;

import spider.bean.InfoContainer;
import spider.node.NodeFilter;

public class SpaceAdnEnterFilter extends NodeFilter{

	@Override
	public String process(String key,String message,InfoContainer container) {
		if (message.contains(" ")){
			message = message.replace(" ", "");
		}
		if (message.contains("\n")){
			message = message.replace("\n", "");
		}
		if (message.contains("\r")){
			message = message.replace("\r", "");
		}
		if (message.contains("&^")){
			message = message.replace("&^", "");
		}
		return message;
	}

}
