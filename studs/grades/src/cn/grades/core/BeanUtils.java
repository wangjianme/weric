package cn.grades.core;

import java.util.Map;

public class BeanUtils {
	public static <T> T populate(Class<T> cls, Map<String, ? extends Object> param) {
		try {
			T t = cls.newInstance();
			org.apache.commons.beanutils.BeanUtils.populate(t, param);
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
