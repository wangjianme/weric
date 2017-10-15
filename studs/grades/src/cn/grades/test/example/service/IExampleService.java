package cn.grades.test.example.service;

import java.util.Map;

import cn.grades.domain.Example;

public interface IExampleService {

	Map<String, Object> query(int page, int rows);
	public Example save(Example exp);
}