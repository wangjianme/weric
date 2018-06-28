package com.topsen.financial.dao.inter;

import java.util.List;
import java.util.Map;
import com.topsen.financial.dao.BaseDAO;
import com.topsen.financial.po.Rebursement;
import com.topsen.financial.po.RebursementItem;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.dao.page.PageableDAO;

public abstract class RebursementDAO extends BaseDAO implements PageableDAO<Rebursement>{
	abstract public Rebursement load(String id);
	public Rebursement load(int id){
		return null;
	}
	public int delete(int id) {
		return 0;
	}

	
	
	abstract public PageModel queryByPage(int curPage,String empno) ;
	abstract public PageModel queryByPage(int curPage,Map<String,String> map) ;
	abstract public PageModel queryByPage(int curPage,int rebNumber);
	abstract public PageModel queryByPageDept(int curPage,Map map);
	abstract public String queryDname(String empno);
	abstract public String queryOname(String empno);
	/**
	 * 2014-5-10 添加  根据 员工编号 和报销单号查询
	 * @param curPage
	 * @param map
	 * @return
	 */
	abstract public PageModel queryByPageWithEMPandREB(int curPage, Map  map) ;
	public int insert(Rebursement t) {
		return 0;
	}
	
	abstract public int insert(Rebursement t,List<RebursementItem> items);

	public List<Rebursement> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
