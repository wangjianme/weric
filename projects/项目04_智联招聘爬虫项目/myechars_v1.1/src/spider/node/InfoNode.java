package spider.node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import spider.bean.InfoBean;
import spider.bean.InfoContainer;
/**
 * 可以处理信息的Node实现类，如果当前节点是信息节点而非URL节点则使用当前类进行解析
 * @author 白宇
 *
 */

public class InfoNode extends Node{
	/**
	 * 节点过滤器集合，可以指定Filter的实现类进行添加，用于对当前处理结果进行过滤
	 */
	private List<NodeFilter> filters = new ArrayList<NodeFilter>();
	/**
	 * 是否为可写节点，处理的结果会根据当前属性决定是否写入文件，如果当前节点为信息节点，并且负责解析内容，但是writed属性为false则处理后的信息依然不会写入文件
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
	 * @param exp 表达式
	 * @param key 关键词，用于后期统计使用
	 * @param writed 即使不是叶节点，但该信息依然写入文件
	 */
	public InfoNode(String exp,String key,boolean writed) {
		super(exp,key);
		this.writed = writed;
	}
	/**
	 * 
	 * @param key 当前信息需要处理的key，在创建节点对象时指定
	 * @param message 需要进行处理的信息
	 * @param container 信息存储容器
	 * @return 处理后的结果，如果为null则不写入文件
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
