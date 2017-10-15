package cn.grades.test.example.dao;

import java.util.Map;

import cn.grades.domain.Example;

public interface IExampleDao {

	Map<String, Object> query(int page, int rows);
	public Example save(Example exp);
}