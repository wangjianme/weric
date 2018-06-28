package com.topsen.financial.service;

import com.topsen.financial.dao.impl.SumDAOImpl;
import com.topsen.financial.dao.inter.SumDAO;
import com.topsen.financial.po.SumRebursement;

public class SumService {
	private SumDAO dao = new SumDAOImpl();
	public int insertOrUpdate(float total,String[] buties,String rebId){
		SumRebursement sum = dao.load(rebId);
		float butie = 0;
		for (String value : buties){
			butie+=Float.parseFloat(value);
		}
		if (sum != null){
			sum.setTotal(total);
			sum.setButie(butie);
			sum.setZongji(total+butie);
			return dao.update(sum);
		}
		sum = new SumRebursement();
		sum.setTotal(total);
		sum.setButie(butie);
		sum.setZongji(total+butie);
		sum.setRebId(rebId);
		return dao.insert(sum);
	}
	
	public SumRebursement query(String rebId){
		return dao.load(rebId);
	}
	
}
