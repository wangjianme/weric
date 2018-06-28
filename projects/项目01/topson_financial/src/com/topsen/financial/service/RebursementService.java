package com.topsen.financial.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topsen.financial.dao.impl.RebursementDAOImpl;
import com.topsen.financial.dao.impl.RebursementDetailDAOImpl;
import com.topsen.financial.dao.inter.RebursementDAO;
import com.topsen.financial.dao.inter.RebursementDetailDAO;
import com.topsen.financial.po.Rebursement;
import com.topsen.financial.po.RebursementDetail;
import com.topsen.financial.po.RebursementItem;
import com.topsen.financial.tempbuild.rebursement.Creater;
import com.topsen.financial.tempbuild.rebursement.Spilter;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.id.CodeCreater;

public class RebursementService {
	private RebursementDAO dao = new RebursementDAOImpl();
	private RebursementDetailDAO detailDAO = new RebursementDetailDAOImpl();
	
	public Rebursement  insertOrUpdateRebursement(Rebursement t, List<RebursementItem> items,List<RebursementDetail> detailList){
		Rebursement oldValue = dao.load(t.getRebId());
		Rebursement rtvReb = null;
		int i = 0;
		if (oldValue == null){
			String rebId = CodeCreater.getInstance().createIdByCharAndTime();
			String rebNumber = CodeCreater.getInstance().createCodeByRandomNumberAndBaseString(t.getEmp().getEmpno());
			t.setRebId(rebId);
			t.setRebNumber(rebNumber);
			t.setFlag("0");
			
		}else{
			t.setRebNumber(oldValue.getRebNumber());
			t.setFlag(oldValue.getFlag());
			rtvReb = t;
		}
		if (items != null){
			for (RebursementItem item : items){
				item.setRebId(t.getRebId());
				//item.setRemark(t.getRemark());自己加的
			}
		}
		i = dao.insert(t, items);
		if (i == 1){
			rtvReb = t;
		}
		if (rtvReb != null){
			detailDAO.insert(detailList, t.getRebId());
		}
		return rtvReb;
	}
	
	public List<RebursementItem> createItemList(List<RebursementDetail> createrList,String startDate,String endDate,String peopleNumber){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Spilter spliter = new Spilter();
		Creater creater = new Creater();
		spliter.addTemplateCreater(creater);
		List<RebursementItem> list = null;
		try {
			Date date1 = format.parse(startDate);
			Date date2 = format.parse(endDate);
			long l1 = date1.getTime();
			long l2 = date2.getTime();
			int dayBetween = (int)((l2-l1)/1000/3600/24);
			list = spliter.createItemList(createrList,dayBetween+0.5f,peopleNumber);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//通过员工编号查询报销单
	public PageModel queryReburementByEmpno(int curPage,String empno){
		return dao.queryByPage(curPage, empno);
	}
	public PageModel queryByRebNumber(int curPage,String empno,String rebNumber){
		Map<String,String> map = new HashMap<String,String>();
		 
		map.put("empno",empno);
		map.put("rebNumber", rebNumber);
		return dao.queryByPage(curPage, map);
	}
	public PageModel queryByCheck(int curPage){
		return dao.queryByPage(curPage);
	}
	//改2
	public PageModel queryByCheckDept(int curPage,Map map){
		return dao.queryByPageDept(curPage, map);
	}
	public String queryDname(String empno){
		return dao.queryDname(empno);
	}
	public String queryOname(String empno){
		return dao.queryOname(empno);
	}
	public PageModel queryCheckByRebNumber(int curPage,int rebNumber){
		return dao.queryByPage(curPage,rebNumber);
	}
	//按员工编号查询2014 -05-10 
	public PageModel queryCheckByRebNumber(int curPage,Map map){
		return dao.queryByPageWithEMPandREB(curPage,map);
		}
	
	public void updateFlag(String rebId,String flag){
		Rebursement reb = dao.load(rebId);
		reb.setFlag(flag);
		dao.update(reb);
	}
	
	public Rebursement queryOne(String rebId){
		return dao.load(rebId);
	}
}
