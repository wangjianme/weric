package spider.node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.bean.InfoBean;
import spider.bean.InfoContainer;
/**
 * ���Դ�����Ϣ��Nodeʵ���࣬�����ǰ�ڵ�����Ϣ�ڵ����URL�ڵ���ʹ�õ�ǰ����н���
 * @author ����
 *
 */

public class InfoNode extends Node{
	/**
	 * �ڵ���������ϣ�����ָ��Filter��ʵ���������ӣ����ڶԵ�ǰ���������й���
	 */
	private List<NodeFilter> filters = new ArrayList<NodeFilter>();
	/**
	 * �Ƿ�Ϊ��д�ڵ㣬����Ľ������ݵ�ǰ���Ծ����Ƿ�д���ļ��������ǰ�ڵ�Ϊ��Ϣ�ڵ㣬���Ҹ���������ݣ�����writed����Ϊfalse��������Ϣ��Ȼ����д���ļ�
	 */
	private boolean writed = false;
	
	public InfoNode addFilter(NodeFilter filter){
		this.filters.add(filter);
		return this;
	}
	
	public InfoNode(String exp) {
		this(exp, "",false);
	}
	public InfoNode(String exp,boolean writed) {
		this(exp, "",writed);
	}
	public InfoNode(String exp,String key) {
		this(exp,key,false);
	}
	/**
	 * 
	 * @param exp ���ʽ
	 * @param key �ؼ��ʣ����ں���ͳ��ʹ��
	 * @param writed ��ʹ����Ҷ�ڵ㣬������Ϣ��Ȼд���ļ�
	 */
	public InfoNode(String exp,String key,boolean writed) {
		super(exp,key);
		this.writed = writed;
	}
	/**
	 * 
	 * @param key ��ǰ��Ϣ��Ҫ�����key���ڴ����ڵ����ʱָ��
	 * @param message ��Ҫ���д������Ϣ
	 * @param container ��Ϣ�洢����
	 * @return �����Ľ�������Ϊnull��д���ļ�
	 */
	private String processFilter(String key,String message,InfoContainer container){
		String rtv = message;
		for (NodeFilter filter : this.filters){
			rtv = filter.process(key,rtv,container);
			if (rtv == null){
				break;
			}
		}
		return rtv;
	}
	@Override
	public List<InfoContainer> process() {
		StringBuffer messageSeq = new StringBuffer();
		List<InfoContainer> result = new ArrayList<InfoContainer>();
		String line = this.getParent().getMessage();
		Pattern pat = Pattern.compile(this.exp,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pat.matcher(line);
		InfoContainer container = this.getContainer();
		while (matcher.find()) {
			String message = matcher.group(1);
			messageSeq.append(message);
			if (!this.hasChildren() || this.writed){
				if (this.filters.size() == 0 || (message = this.processFilter(this.getKey(),message,container)) != null){
					InfoBean bean = new InfoBean();
					bean.setBeanKey(this.getKey());
					bean.setBeanValue(message);
					container.addInfo(this.getKey(), bean);
				}
			}
		}
		this.setMessage(messageSeq.toString().toLowerCase());
		result.addAll(this.processChildren(container));
		return result;
	}
}
