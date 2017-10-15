package com.myexam.teac.login.dao;

import java.util.Map;

import com.myexam.domain.Teac;
/**
 * 用户登录功能
 * @author wangjianme
 * @version 1.0,2010-10-14
 */
public interface ILoginDao {
	Map<String,Object>  login(Teac teac);
}
