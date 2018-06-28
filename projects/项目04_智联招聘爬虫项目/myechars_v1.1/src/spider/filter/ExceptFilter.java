package spider.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.bean.InfoContainer;
import spider.node.NodeFilter;
/**
 * ���������������ʵ�ַ�������Ҫ�Թؼ���(keyWord�ֶ�)�����˹���
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
		//�ж�����ƥ������ȥ�عؼ���,���⵱ǰҳ�淴�����ֹؼ��ʽ��ж�δ洢Ӱ���������������
		if (!removeMatcher.find()&&!container.contains(key, message)) {
			return message;
		}
		return null;
	}

}
