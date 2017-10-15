package cn.sms.core;

import cn.sms.utils.QueryRunner;

public class BaseDao {
	protected QueryRunner run;

	public BaseDao(){
		run = new QueryRunner(DataSourceUtils.getDataSource());
	}
}
