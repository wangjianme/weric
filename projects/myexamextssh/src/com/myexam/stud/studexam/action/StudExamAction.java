package com.myexam.stud.studexam.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.myexam.domain.Answ;
import com.myexam.domain.Info;
import com.myexam.domain.Openexam;
import com.myexam.domain.Stud;
import com.myexam.pub.CurrentTime;
import com.myexam.pub.JSONUtil;
import com.myexam.stud.studexam.service.IStudExamService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 学生参加考试
 * @author wangjianme
 * @version 1.0,2010-11-28
 */
@SuppressWarnings("serial")
public class StudExamAction extends ActionSupport implements ModelDriven<Openexam> {
	/**
	 * 只负责转到操作页面
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
	/**
	 * 查询此学生已经开通的考试
	 */
	public String queryOpenedExam() throws Exception{
		Stud stud = (Stud)ActionContext.getContext().getSession().get("stud");
		String studId = stud.getStudId();						//获取学生id
		Map<String,String> param = new HashMap<String, String>();
		param.put("studId", studId);
		Map<String,Object> result = getService().queryOpenedExam(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 确定开始考试
	 */
	public String beginExaming() throws Exception{
		String oeId = openexam.getOeId();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("oeId",oeId);
		System.err.println("参数为>>>:"+param);
		Map<String,Object> result = getService().beginExaming(param);
		if(result.get("examIsOver").equals(false)){						//说明考试没有完成,要保存Session中信息
			System.err.println("没有考试完成，保存session\n"+result);
			ActionContext.getContext().getSession().put("exam",result);
		}
		Map<String,Object> jm = new HashMap<String, Object>();
		jm.put("examIsOver",result.get("examIsOver"));
		jm.put("infoId",result.get("infoId"));
		jm.put("success",result.get("success"));
		String json = JSONUtil.toJsonString(jm);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 转到考试页面
	 */
	public String toExamingPage() throws Exception{
		return "examingPage";
	}
	/**
	 * 查询一个试题
	 * @return
	 * @throws Exception
	 */
	private String infoId;
	private String quesId;
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getQuesId() {
		return quesId;
	}
	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}
	public String queryOneQues() throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("quesId",getQuesId());
		param.put("infoId",getInfoId());
		Map<String,Object> eq = getService().queryOneExamQues(param);
		ActionContext.getContext().put("equs",eq);
		return "oneques";
	}
	/**
	 * 保存学生的答案
	 */
	private String answId;			//答案的id,如果已经做过此试题则此值存在，否则此值为空
	private String eqId;			//试题的id
	private String[] values;		//答案
	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String getAnswId() {
		return answId;
	}
	public void setAnswId(String answId) {
		this.answId = answId;
	}
	@SuppressWarnings("unchecked")
	public String saveAnsw() throws Exception{
		System.err.println(">>>>>>>>>>:"+getEqId());
		String vv = "";
		if(getValues()!=null){
			for(String s : getValues()){
				if(!s.trim().equals("")){
					if(vv.equals("")){
						vv = s;
					}else{
						vv = vv+","+s;				//以前使用的是###现在修改成使用,逗号分开
					}
				}
			}
		}
		System.err.println("values is:"+vv);
		Map<String,Object> result = new HashMap<String, Object>();
		if(!vv.equals("")){
			result.put("blank",false);
			Map<String,Object> exam = (Map<String,Object>)ActionContext.getContext().getSession().get("exam");	
			String infoId = (String)exam.get("infoId");
			Answ answ = new Answ();
			if(getAnswId()!=null && !getAnswId().equals("")){
				answ.setAnswId(getAnswId());
			}
			answ.setAnswEq(getEqId());						//保存试题id
			answ.setAnswInfo(infoId);						//保存考试信息id
			answ.setAnswAnswer(vv);							//保存答案
			answ.setAnswScore(0);							//先将分数设置为0
			
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("answ", answ);
			param = getService().saveAnsw(param);
			result.putAll(param);
		}else{
			result.put("blank",true);				//没有填写答案为空值
			result.put("success", true);
			System.err.println("没有填写答案。。。");
		}
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}
	/**
	 * 计算总分
	 * 参数：oeId,infoId,infoTimein,infoEtime
	 */
	@SuppressWarnings("unchecked")
	public String calculateScore() throws Exception{
		Map<String,Object> exam = (Map<String,Object>)ActionContext.getContext().getSession().get("exam");
		String oeId 		= (String)exam.get("oeId");						//从Session中取出oeId下同
		String infoId 		= (String)exam.get("infoId");
		String infoBtime    = (String)exam.get("infoBtime");
		Date infoEtimeDate    = new Date();
		Date infoBtimeDate  = CurrentTime.getDate(infoBtime);
		Integer infoTimein  =(int)((infoEtimeDate.getTime()-infoBtimeDate.getTime())/(1000*60));	//计算用时
		if(infoTimein<=0){			//如果没有1分钟，则默认为1分钟
			infoTimein = 1;
		}
		Map<String,Object>  param = new HashMap<String, Object>();
		param.put("oeId",oeId);
		param.put("infoId",infoId);
		param.put("infoTimein",infoTimein);
		param.put("infoEtime",CurrentTime.getDate(infoEtimeDate));
		
		Map<String,Object> result = getService().calculateScore(param);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
	/**
	 * 查询考试结果，只需要一个参数
	 */
	@SuppressWarnings("unchecked")
	public String examScore() throws Exception{
		Map<String,Object> exam = (Map<String,Object>)ActionContext.getContext().getSession().get("exam");
		String infoId = (String)exam.get("infoId");
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("infoId",infoId);
		Map<String,Object> result = getService().examScore(param);
		result.put("examName",exam.get("examName"));
		result.put("courName",exam.get("courName"));
		result.put("examPass",exam.get("examPass"));
		result.put("examScore",exam.get("examScore"));
		System.err.println("查询分数的结果为："+result);
		ActionContext.getContext().put("examScore",result);
		return "examscore";
	}
	
	/**
	 * 模式驱动
	 */
	private Openexam openexam = new Openexam();
	public Openexam getModel() {
		return openexam;
	}
	private IStudExamService service;				//注入
	public IStudExamService getService() {
		return service;
	}
	public void setService(IStudExamService service) {
		this.service = service;
	}
}
