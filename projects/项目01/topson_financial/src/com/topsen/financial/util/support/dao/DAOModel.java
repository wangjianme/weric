package com.topsen.financial.util.support.dao;

import java.util.List;


public interface DAOModel<T> {
	
	abstract public T load(int id);
	
	abstract public int update(T t);
	
	abstract public int delete(int id);
	
	abstract public int insert(T t);
	
	abstract public List<T> queryAll();
}
