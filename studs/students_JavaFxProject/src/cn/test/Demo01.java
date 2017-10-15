package cn.test;

import java.util.List;

import org.junit.Test;

import cn.stud.db.DataBase;
import cn.stud.domain.Stud;

public class Demo01 {
	@Test
	public void testt() {
		Stud stud = new Stud();
		stud.setName("赵");
		stud.setSex("男");
		stud.setAge2(20);
		List<Stud> ss = DataBase.getInstace().queryWithStud(stud);
		System.err.println(">>>>>>>>>>>:" + ss);

	}
}
