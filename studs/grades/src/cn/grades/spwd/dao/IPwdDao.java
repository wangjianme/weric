package cn.grades.spwd.dao;

import java.util.List;

import cn.grades.domain.Stud;


public interface IPwdDao {

	 int updatePwd(Stud stud,String newPwd);
}