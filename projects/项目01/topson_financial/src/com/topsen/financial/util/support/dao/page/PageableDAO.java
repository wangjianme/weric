package com.topsen.financial.util.support.dao.page;

import com.topsen.financial.util.support.dao.DAOModel;
import com.topsen.financial.util.support.dao.bean.PageModel;

public interface PageableDAO<T> extends DAOModel<T>{
	public PageModel queryByPage(int curPage);
}
