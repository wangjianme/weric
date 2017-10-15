package com.myexam.teac.exam.ques.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.myexam.domain.Ques;
import com.myexam.domain.Teac;
import com.myexam.pub.CurrentTime;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.exam.ques.service.IQuesService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 题库管理
 * @author wangjianme
 * @version 1.0,2010-10-25
 */
@SuppressWarnings("serial")
public class QuesAction extends ActionSupport implements ModelDriven<Ques> {
	private IQuesService service;
	public IQuesService getService() {
		return service;
	}
	public void setService(IQuesService service) {
		this.service = service;
	}
	/**
	 * 转到试题页面
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 转到试题增加页面
	 */
	public String toAdd() throws Exception{
		return "add";
	}
	/**
	 * 查询
	 */
	private Integer start;
	private Integer limit;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String query() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ques",ques);
		param.put("start",getStart());
		param.put("limit",getLimit());
		Map<String,Object> result = getService().query(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 保存
	 */
	String[] values;
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public String save() throws Exception{
		String quesJson = JSONObject.fromObject(ques).toString();
		System.err.println("ques>>>:"+quesJson);
		System.err.println("VV:"+getValues());
		String quesChoose = "";
		if(getValues()!=null){
			for(String s : getValues()){
				System.err.println(">>>:"+s);
				if(quesChoose.equals("")){
					quesChoose=s;
				}else{
					quesChoose=quesChoose+"###"+s;
				}
			}
		}
		ques.setQuesChoose(quesChoose);
		ques.setQuesTime(CurrentTime.getDate());
		ques.setQuesState("1");
		Teac t = (Teac)ActionContext.getContext().getSession().get("teac");
		ques.setQuesMaker(t.getTeacName());
		ques.setQuesBody(ques.getQuesBody().replaceAll("'", "\""));			//将单引号换成双引号
		ques.setQuesSolu(ques.getQuesSolu().replaceAll("，",","));			//将中文的逗号换成英文的逗号
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("ques", ques);
		Map<String,Object> result = getService().save(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 查询课程
	 */
	private String courName;
	public String getCourName() {
		return courName;
	}
	public void setCourName(String courName) {
		this.courName = courName;
	}
	public String queryCour() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("courName",getCourName());
		param.put("start", getStart());
		param.put("limit", getLimit());
		Map<String,Object> result = getService().queryCour(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 查询一条试题
	 */
	public String queryOneQues() throws Exception{
		Map<String,Object> result = getService().queryOneQues(ques.getQuesId());
		ActionContext.getContext().put("ques",result.get("ques"));
		return "view";
	}
	/**
	 * 删除试题
	 */
	public String del() throws Exception{
		Map<String,Object> result = getService().del(ques.getQuesId());
		if(result.get("success").equals(true)){
			String body = (String)result.get("body");
			if(body!=null && !body.equals("")){
				String path = ServletActionContext.getServletContext().getRealPath("/images/ques");
				int len = 0;
				while(true){		//开始删除jpg的文件
					len = body.indexOf(".jpg",len+1);
					if(len==-1){
						break;
					}
					String img = body.substring(len-32,len+4);
					img = path+"/"+img;
					System.err.println("删除文件:"+img);
					File file = new File(img);
					if(file.exists()){
						file.delete();
					}
				}
			}
			result.remove("body");
		}
		String json = JSONUtil.toJsonString(result);
		System.err.println(json);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 模式驱动
	 */
	private Ques ques = new Ques();
	public Ques getModel() {
		return ques;
	}
}
