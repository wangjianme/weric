package cn.grades.clas.service;

import java.util.List;
import java.util.Map;

import cn.grades.domain.Clas;
import cn.grades.domain.Department;
import cn.grades.domain.Grade;
import cn.grades.domain.Major;

public interface IClasService {

	/* (non-Javadoc)
	 * @see cn.grades.clas.service.IClasService#query()
	 */
	/* (non-Javadoc)
	 * @see cn.grades.clas.service.IClasService#query()
	 */
	Map<String, Object> query(int page, int rows);

	/* (non-Javadoc)
	 * @see cn.grades.clas.service.IClasService#save(cn.grades.domain.Clas)
	 */
	/* (non-Javadoc)
	 * @see cn.grades.clas.service.IClasService#save(cn.grades.domain.Clas)
	 */
	Clas save(Clas clas);

	int delete(String id);

	int update(Clas clas);

	List<Department> text();

	List<Major> majorVal();

	List<Grade> name();
    Map<String, Object> select (Clas clas,int pag,int row);


}