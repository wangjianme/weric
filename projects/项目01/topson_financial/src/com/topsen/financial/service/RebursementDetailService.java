package com.topsen.financial.service;

import java.util.List;

import com.topsen.financial.dao.impl.RebursementDetailDAOImpl;
import com.topsen.financial.dao.inter.RebursementDetailDAO;
import com.topsen.financial.po.RebursementDetail;

public class RebursementDetailService {
	private RebursementDetailDAO dao = new RebursementDetailDAOImpl();
	
	public int insert(List<RebursementDetail> list,String rebId){
		return dao.insert(list,rebId);
	}
	
	public List<RebursementDetail> queryByRebId(String rebId){
		return dao.queryByRebId(rebId);
	}
	
	public List<Integer> queryDistinctTempId(String rebId){
		return dao.queryDistinctTempId(rebId);
	}
}
