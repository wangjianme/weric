package com.myexam.teac.examadd.action;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.myexam.domain.Teac;
import com.myexam.pub.CurrentTime;
import com.myexam.pub.JSONUtil;
import com.myexam.teac.examadd.domain.ExamAdd;
import com.myexam.teac.examadd.service.IExamAddService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 试卷增加
 * @author wangjianme
 * @version 1.0,2010-10-31
 */
@SuppressWarnings("serial")
public class ExamAddAction extends ActionSupport implements ModelDriven<ExamAdd>{
	private IExamAddService service;
	
	public IExamAddService getService() {
		return service;
	}

	public void setService(IExamAddService service) {
		this.service = service;
	}
	/**
	 * 保存
	 */
	public String save() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		Teac t = (Teac)ActionContext.getContext().getSession().get("teac");
		examadd.setExamMaker(t.getTeacName());
		examadd.setExamMktime(CurrentTime.getDate());
		System.err.println(">>>>:"+JSONObject.fromObject(examadd).toString());
		param.put("examadd", examadd);
		param = getService().save(param);
		String json = JSONUtil.toJsonString(param);
		System.err.println(">>>:"+json);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	
	/**
	 * 模式驱动
	 */
	private ExamAdd examadd = new ExamAdd();
	public ExamAdd getModel() {
		return examadd;
	}
	
}
