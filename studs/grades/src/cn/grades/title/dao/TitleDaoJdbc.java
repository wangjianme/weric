package cn.grades.title.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.grades.core.BaseDao;
import cn.grades.core.DSUtils;
import cn.grades.domain.Title;
import cn.grades.domain.*;

public class TitleDaoJdbc extends BaseDao implements ITitleDaoJdbc {
	
      public Title save(Title title){
    	  title.setId(UUID.randomUUID().toString().replace("-",""));
    	  String sql = "insert into title values(?,?,?)";
    	  run.update(sql,title.getId(),title.getName(),title.getDes());
          return title;
      }
      
      
      /*
       * public void delete(String[] ids){
    	  Connection con = null;
    	  String sql = "delete from title where title_id=?";
    	  try{
    		  con = DSUtils.getDataSource().getConnection();
    		  con.setAutoCommit(false);
    		  for(String id : ids){
    			  run.update(sql,id);
    			  System.err.println(id);
    		  }
    		  con.commit();
    		  }catch(Exception e){
    			  try{
    				  con.rollback();
    			  }catch(SQLException e1){
    				  e1.printStackTrace();
    			  }
    		  }finally{
    			  try{
    				  con.setAutoCommit(true);
    				  con.close();
    			  }catch(SQLException e){
    				  e.printStackTrace();
    			  }
    		  }
      }
       * */
      //开发删除功能
      public int delete(String id){
    	  String ids[] = id.split(";");
    	  int rows = 0;
    	  for(int i = 0;i<ids.length;i++){
    		  String sql = "delete from title where title_id=?";
    		  rows = run.update(sql,ids[i]);
    	  }
    	  return rows;
      }
      
      //修改
      public int update(Title title){
    	  String sql = "update title set title_name=?,title_desc=? where title_id=?";
    	  System.out.println("=="+title.getName()+title.getDes()+title.getId());
    	  int rows = run.update(sql,title.getName(),title.getDes(),title.getId());
    	  return rows;
      }
      //进入模块就显示数据
	public List<Title> query() {
			String sql = "select title_id id,title_name name,title_desc des from title";
			List<Title> list = run.query(sql, new BeanListHandler<Title>(Title.class));
			System.err.println("------"+list.toString());
			return list;
		}
	
	//查询
	public List select(Title title) {
		//Map<String, Object> map = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		String sql = "select title_id as id,title_name as name,title_desc as des from title where 1=1 ";
		//String where = " where 1=1";
		if (!(title.getName() == null || title.getName().equals(""))) {
			sql += " and title_name like ?";
			params.add("%" + title.getName().trim() + "%");
			System.err.println("------------"+params);
		}
		System.out.println("+++++++++++name:" + title.getName());
		List<Title> list = run.query(sql, new BeanListHandler<Title>(Title.class),params.get(0));
		System.out.println("============"+params.get(0));
		System.err.println(list);
		return list;
	}

}
      

