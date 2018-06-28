package com.topsen.financial.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.topsen.financial.dao.inter.RebursementDAO;
import com.topsen.financial.po.Rebursement;
import com.topsen.financial.po.RebursementItem;
import com.topsen.financial.util.support.dao.bean.PageModel;
import com.topsen.financial.util.support.dao.page.PageModelFactory;

public class RebursementDAOImpl extends RebursementDAO{


	public Rebursement load(String id) {
		Rebursement reb = null;
		try {
			reb = (Rebursement) this.getMap().queryForObject("reb_space.one",id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reb;
	}

	public int update(Rebursement t) {
		int i = 0;
		try {
			this.getMap().update("reb_space.update",t);
			i = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int insert(Rebursement t, List<RebursementItem> items) {
		int i = 0;
		try {
			this.getMap().startTransaction();
			this.getMap().startBatch();
			this.getMap().delete("reb_item_space.delete", t.getRebId());
			this.getMap().delete("reb_space.delete", t.getRebId());
			System.out.println(t.getRebId());
			this.getMap().insert("reb_space.insert", t);
			System.out.println("=========================================>"+items.size());
			if (items != null){
				for (RebursementItem item : items){
					this.getMap().insert("reb_item_space.insert", item);
				}
			}
			this.getMap().executeBatch();
			this.getMap().commitTransaction();
			i = 1;
		} catch (SQLException e) {
			try {
				this.getMap().getCurrentConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}finally{
			try {
				this.getMap().endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	@Override
	public PageModel queryByPage(int curPage, String empno) {
		return PageModelFactory.getFactory().createPageModel("reb_space.queryByPage", empno,"reb_space.count",empno, curPage);
	}

	@Override
	public PageModel queryByPage(int curPage, Map<String, String> map) {
		return PageModelFactory.getFactory().createPageModel("reb_space.pageByRebNumber", map,"reb_space.countByRebNumber",map, curPage);
	}
	public PageModel queryByPage(int curPage) {
		return PageModelFactory.getFactory().createPageModel("reb_space.pageByCheck","reb_space.countByCheck", curPage);
	}

	@Override
	public PageModel queryByPage(int curPage, int rebNumber) {
		return PageModelFactory.getFactory().createPageModel("reb_space.pageCheckByRebNumber", rebNumber,"reb_space.countCheckByRebNumber",rebNumber, curPage);
	}
	//2014-5-10 根据单号和员工号 查询  需要传入map 调用 pageCheckByRebNumber sql 添加如下方法
   public PageModel queryByPageWithEMPandREB(int curPage, Map  map) {
		return PageModelFactory.getFactory().createPageModel("reb_space.pageCheckByRebNumber", map,"reb_space.countCheckByRebNumber",map, curPage);
	}

@Override
/*public PageModel queryByPageDept(int curPage) {
	return PageModelFactory.getFactory().createPageModel("reb_space.queryByPageDept","reb_space.countPageDept", curPage);
}*/
public PageModel queryByPageDept(int curPage,Map map) {
	return PageModelFactory.getFactory().createPageModel("reb_space.queryByPageDept",map,"reb_space.countPageDept",map, curPage);
}

@Override//按工号查询部门 sql:select 部门名称 from dbo.员工基本信息表 a,dbo.岗位说明书 b,dbo.部门表 c where a.岗位说明书ID = b.岗位说明书ID and b.所属部门ID = c.部门ID and 工号 = '0166'
public String queryDname(String empno) {
	String dname = null;
	try {
		dname = (String) this.getMap().queryForObject("reb_space.queryDname",empno);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return dname;
}

@Override
public String queryOname(String empno) {
	String oname = null;
	try {
		oname = (String) this.getMap().queryForObject("reb_space.queryOname",empno);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return oname;
}
}
