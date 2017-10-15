package cn.grades.slogin.dao;

import java.util.List;

import cn.grades.domain.Score;
import cn.grades.domain.Stud;
import cn.grades.domain.User;

public interface ILoginDao {
	List<Stud> query(String id);
	Stud login1(Stud stud);
	List<Score> queryscore(String id,Score s);
}