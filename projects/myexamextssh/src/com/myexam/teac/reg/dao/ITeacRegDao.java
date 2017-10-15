package com.myexam.teac.reg.dao;

import com.myexam.domain.Teac;

public interface ITeacRegDao {
	boolean reg(Teac teac);
	boolean validate(Teac teac);
}
