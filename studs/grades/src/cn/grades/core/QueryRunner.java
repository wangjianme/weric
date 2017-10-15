package cn.grades.core;

import javax.sql.DataSource;
import org.apache.commons.dbutils.ResultSetHandler;

public class QueryRunner extends org.apache.commons.dbutils.QueryRunner {
	public QueryRunner() {
		super();
	}

	public QueryRunner(DataSource ds) {
		super(ds);
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) {
		try {
			return super.query(sql, rsh);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... objs) {
		try {
			return super.query(sql, rsh, objs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int update(String sql, Object... params) {
		try {
			return super.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int update(String sql, Object param) {
		try {
			return super.update(sql, param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
