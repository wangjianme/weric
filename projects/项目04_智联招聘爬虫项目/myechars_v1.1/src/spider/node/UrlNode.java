package spider.node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.bean.InfoBean;
import spider.bean.InfoContainer;


/**
 * 用于解析为URL的子节点
 * @author 白宇
 *
 */
public class UrlNode extends Node{
	public final static String URL="com.oracle.url";
	

	public UrlNode(String exp, String key) {
		super(exp, key);
	}
	public UrlNode(String exp) {
		super(exp, "");
	}
	@Override
	public List<InfoContainer> process() {
		List<InfoContainer> result = new ArrayList<InfoContainer>();
		Node parent = this.getParent();
		if (parent != null){
			String line = this.getParent().getMessage();
			Pattern pat = Pattern.compile(this.exp,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pat.matcher(line);
			while (matcher.find()) {
				InfoContainer container = new InfoContainer();
				String urlInfo = matcher.group(1);
				InfoBean bean = new InfoBean();
				bean.setBeanKey(UrlNode.URL);
				bean.setBeanValue(urlInfo);
				container.addInfo(UrlNode.URL, bean);
				System.out.println("URL节点:"+urlInfo);
				String httpLine = this.executeHttp(urlInfo);
				this.setMessage(httpLine);
				result.addAll(this.processChildren(container));
				result.add(container);
			}
		}
		return result;
	}
	
}
