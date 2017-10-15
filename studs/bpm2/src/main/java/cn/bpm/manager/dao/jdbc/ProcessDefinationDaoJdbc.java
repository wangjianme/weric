package cn.bpm.manager.dao.jdbc;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.bpm.manager.dao.IProcessDefinationDao;

/**
 * 继承SqlSessionDaoSupport则不用手工执行Session.close方法了
 * @author Administrator
 *
 */
@Repository(value = "processDefinationDao")
public class ProcessDefinationDaoJdbc extends SqlSessionDaoSupport implements IProcessDefinationDao {
	/**
	 * 为了设置，所以调用super.xx
	 */
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public List<Map<String, Object>> queryAllProcessDefination() {
		List<Map<String, Object>> list = getSqlSession().selectList("processDefination.processDefinationQuery");
		return list;
	}
}
