package com.topsen.financial.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.topsen.financial.po.BackMessage;
import com.topsen.financial.po.Deptment;
import com.topsen.financial.po.Emploee;
import com.topsen.financial.po.Rebursement;
import com.topsen.financial.po.RebursementDetail;
import com.topsen.financial.po.RebursementItem;
import com.topsen.financial.po.SumRebursement;
import com.topsen.financial.po.Template;
import com.topsen.financial.po.TemplateItem;
import com.topsen.financial.service.DeptService;
import com.topsen.financial.service.MessageService;
import com.topsen.financial.service.RebursementDetailService;
import com.topsen.financial.service.RebursementService;
import com.topsen.financial.service.SumService;
import com.topsen.financial.service.TemplateItemService;
import com.topsen.financial.service.TemplateService;
import com.topsen.financial.tempbuild.detail.DetailCreater;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.struts2.BaseAction;
import com.topsen.financial.util.template.bean.TemplateBean;

public class RebursementAction extends BaseAction implements ModelDriven<Rebursement>{
	private DeptService service = new DeptService();
	private MessageService messageService = new MessageService();
	private RebursementService rebService = new RebursementService();
	private TemplateService tempService = new TemplateService();
	private TemplateItemService itemService = new TemplateItemService();
	private SumService sumService = new SumService();
	private SumRebursement sum ;
	private RebursementDetailService detailService = new RebursementDetailService();
	private List<Deptment> deptList;
	private List<Integer> distinctTempList;
	private List<Template> tempList;
	private List<RebursementItem> itemList;
	private List<TemplateBean> beanList;
	private List<RebursementDetail> detailList;
	private PageModel pageModel;
	private Rebursement rebursement = new Rebursement();
	public SumRebursement getSum() {
		return sum;
	}
	public List<TemplateBean> getBeanList() {
		return beanList;
	}
	public List<RebursementItem> getItemList() {
		return itemList;
	}
	public List<Integer> getDistinctTempList() {
		return distinctTempList;
	}
	public List<RebursementDetail> getDetailList() {
		return detailList;
	}
	public Rebursement getModel() {
		return rebursement;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public List<Template> getTempList() {
		return tempList;
	}
	public Rebursement getRebursement() {
		return rebursement;
	}
	public List<Deptment> getDeptList() {
		return deptList;
	}
	public String queryDept(){
		deptList = service.queryAll();
		return "stepone";
	}
	
	public String saveRebursement(){
		String path = this.getRequest().getParameter("path");
		Emploee emp = (Emploee)this.getRequest().getSession().getAttribute("emp");
		String rebId = getRequest().getParameter("rebId");
		String peopleNumber = getRequest().getParameter("peopleNumber");
		rebursement.setRebId(rebId);
		rebursement.setEmp(emp);
		rebursement.setPeopleNumber(Integer.parseInt(peopleNumber));
		String[] itemIds = this.getRequest().getParameterValues("itemId");
		String[] beginPlaces = this.getRequest().getParameterValues("beginPlace");
		String[] endPlaces = this.getRequest().getParameterValues("endPlace");
		String[] beginDates = this.getRequest().getParameterValues("beginDate");
		String[] endDates = this.getRequest().getParameterValues("endDate");
		String[] cityTraffics = this.getRequest().getParameterValues("cityTraffic");
		String[] otherses = this.getRequest().getParameterValues("others");
		String[] zsMoneys = this.getRequest().getParameterValues("zsMoney");
		String[] buties = this.getRequest().getParameterValues("butie");
		String[] totals = this.getRequest().getParameterValues("total");
		String totalMoney = this.getRequest().getParameter("totalMoney");
		List<RebursementItem> itemsList = new ArrayList<RebursementItem>();
		for (int i = 0;i < itemIds.length;i++){
			RebursementItem item = new RebursementItem();
			item.setItemId(Integer.parseInt(itemIds[i]));
			item.setBeginPlace(beginPlaces[i]);
			item.setBeginDate(beginDates[i]);
			item.setEndDate(endDates[i]);
			item.setEndPlace(endPlaces[i]);
			item.setCityTraffic(Float.parseFloat(cityTraffics[i]));
			item.setOthers(Float.parseFloat(otherses[i]));
			item.setZsMoney(Float.parseFloat(zsMoneys[i]));
			item.setButie(Float.parseFloat(buties[i]));
			item.setTotal(Float.parseFloat(totals[i]));
			itemsList.add(item);
		}
		List<RebursementDetail> list = (List<RebursementDetail>)getSession().getAttribute("detailList");
		rebursement = rebService.insertOrUpdateRebursement(rebursement, itemsList,list);
		
		if (rebursement != null && rebursement.getRebId() != null){
			getSession().removeAttribute("detailList");
			sumService.insertOrUpdate(Float.parseFloat(totalMoney), buties, rebursement.getRebId());
		}
		if (path != null){
			return null;
		}
		return "finish";
	}
	public String queryAllTemplate(){
		tempList = tempService.queryAll();
		return "steptwo";
	}
	
	public String updateRebursement(){
		this.saveRebursement();
		this.addDetailValue();
		return this.queryMyRebursement();
	}
	
	public String addDetailValue(){
		List<RebursementDetail> list = new ArrayList<RebursementDetail>();
		List<RebursementDetail> createrList = new ArrayList<RebursementDetail>();
		String[] tempIds = this.getRequest().getParameterValues("temp");
		String startDate = getRequest().getParameter("startDate");
		String backDate = getRequest().getParameter("backDate");
		String peopleNumber = getRequest().getParameter("peopleNumber");
		if (tempIds != null){
			for (String stempId : tempIds){
				int tempId = Integer.parseInt(stempId);
				List<TemplateItem> itemList = itemService.queryByTempId(tempId);
				Template template = new Template();
				template.setTempId(tempId);
				int orderValue = 1;
				for (TemplateItem item : itemList){
					String[] values = this.getRequest().getParameterValues("detailValue-"+item.getTempItemId());
					int i = 1;
					for (String value:values){
						RebursementDetail detail = new RebursementDetail();
						detail.setItem(item);
						detail.setDetailValue(value);
						detail.setGroupId(i++);
						detail.setTypeId(1);
						detail.setTemplate(template);
						detail.setOrderValue(orderValue);
						list.add(detail);
						createrList.add(detail);
					}
					createrList.remove(createrList.size()-1);
					orderValue++;
				}
			}
		}
		getSession().setAttribute("detailList", list);
		itemList = this.rebService.createItemList(createrList, startDate, backDate,peopleNumber);
		return "stepthree";
	}
	public String queryMyRebursement(){
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		Emploee emp = (Emploee)getSession().getAttribute("emp");
		pageModel = rebService.queryReburementByEmpno(Integer.parseInt(curPage), emp.getEmpno());
		return "showMine";
	}
	
	public String updateFlag(){
		String rebId = this.getRequest().getParameter("rebId");
		String flag = this.getRequest().getParameter("flag");
		String backPath = this.getRequest().getParameter("backPath");
		rebService.updateFlag(rebId, flag);
		if (backPath == null){
			return queryMyRebursement();
		}else if (backPath.equals("deptCheck")){
			return queryDeptRebursementCheck();
		}
		return queryMyRebursementCheck();
	}
	
	public String queryOne(){
		String rebId = this.getRequest().getParameter("rebId");
		String path = this.getRequest().getParameter("path");
		detailList = detailService.queryByRebId(rebId);
		rebursement = rebService.queryOne(rebId);
		this.queryAllTemplate();
		distinctTempList = detailService.queryDistinctTempId(rebId);
		DetailCreater creater = new DetailCreater();
		beanList = creater.createItemList(detailList);
		if (path != null){
			sum = sumService.query(rebId);
			return path;
		}
		return "view";
	}
	
	public String queryByRebNumber() throws UnsupportedEncodingException{
		this.getRequest().setCharacterEncoding("utf-8");
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		String rebNumber = getRequest().getParameter("rebNumber");
		Emploee emp = (Emploee)getSession().getAttribute("emp");
		pageModel = rebService.queryByRebNumber(Integer.parseInt(curPage), emp.getEmpno(), rebNumber);
		return "showMine";
	}
	public String queryMyRebursementCheck(){
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		pageModel = rebService.queryByCheck(Integer.parseInt(curPage));
		return "showCheck";
	}
	public String queryCheckByRebNumber() throws UnsupportedEncodingException{
		
		this.getRequest().setCharacterEncoding("UTF-8");
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		String rebNumber = getRequest().getParameter("rebNumber");
		//获取员工编号
		String empno=getRequest().getParameter("empno");
		//empno=new String(empno.getBytes("iso-8859-1"),"utf-8");
		Map<String,String> map=new HashMap<String,String>();
		map.put("empno", empno);
		map.put("reb_number", rebNumber); 
		pageModel = rebService.queryCheckByRebNumber(Integer.parseInt(curPage),map );
		return "showCheck";
	}
	//2014-5-21 部门审核按报销单编号、工号进行查询
   public String queryCheckByRebNumber1() throws UnsupportedEncodingException{
		
		this.getRequest().setCharacterEncoding("UTF-8");
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		String rebNumber = getRequest().getParameter("rebNumber");
		//获取员工编号
		String empno=getRequest().getParameter("empno");
		Map map=new HashMap();
		map.put("empno", empno);
		map.put("reb_number", rebNumber); 
		pageModel = rebService.queryCheckByRebNumber(Integer.parseInt(curPage),map );
		return "showDeptCheck";
	}
	
	public String back(){
		String rebId = this.getRequest().getParameter("rebId");
		String message = this.getRequest().getParameter("message");
		BackMessage backMessage = new BackMessage();
		backMessage.setRebId(rebId);
		backMessage.setMessage(message);
		messageService.insert(backMessage);
		return updateFlag();
	}
	
	public String queryMessage(){
		String rebId = this.getRequest().getParameter("rebId");
		BackMessage message = messageService.query(rebId);
		this.getRequest().setAttribute("message", message);
		return "message";
	}
	//改
	public String queryDeptRebursementCheck(){
		String curPage = this.getRequest().getParameter("curPage");
		if (curPage == null){
			curPage = "1";
		}
		String empno = (String) getRequest().getSession().getAttribute("empno");
		String dname = rebService.queryDname(empno);
		String oname = rebService.queryOname(empno);
		Map map=new HashMap();
		map.put("dname", dname);
		map.put("oname", oname); 
		pageModel = rebService.queryByCheckDept(Integer.parseInt(curPage), map);
		return "showDeptCheck";
	}
}
