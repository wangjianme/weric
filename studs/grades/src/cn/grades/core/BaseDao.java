package cn.grades.core;
public class BaseDao {
	protected QueryRunner run;
	public BaseDao() {
		run = new QueryRunner(DSUtils.getDataSource());
	}
}
