package spider.node;

import spider.bean.InfoContainer;


public abstract class NodeFilter{
	protected String exp;
	public NodeFilter(String exp){
		this.exp = exp;
	}
	public NodeFilter(){}
	/**
	 * ������������,�������ֵΪnull�������Ϣ������д洢
	 * @param key
	 * @param message
	 * @param container
	 * @return
	 */
	abstract public String process(String key,String message,InfoContainer container);
}
