package spider.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.bean.InfoContainer;
import spider.node.NodeFilter;
/**
 * 例外过滤器，其中实现方法中主要对关键词(keyWord字段)进行了过滤
 * @author Administrator
 *
 */
public class ExceptFilter extends NodeFilter{

	public ExceptFilter(String exp) {
		super(exp);
	}

	@Override
	public String process(String key,String message,InfoContainer container) {
		Pattern removePat = Pattern.compile(this.exp, Pattern.CASE_INSENSITIVE);
		Matcher removeMatcher = removePat.matcher(message);
		//判断正则匹配结果和去重关键词,避免当前页面反复出现关键词进行多次存储影响分析结果的情况。
		if (!removeMatcher.find()&&!container.contains(key, message)) {
			return message;
		}
		return null;
	}

}
