package spider.node;

import spider.bean.InfoContainer;


public abstract class NodeFilter{
	protected String exp;
	public NodeFilter(String exp){
		this.exp = exp;
	}
	public NodeFilter(){}
	/**
	 * 过滤器处理方法,如果返回值为null，则该信息不会进行存储
	 * @param key
	 * @param message
	 * @param container
	 * @return
	 */
	abstract public String process(String key,String message,InfoContainer container);
}
