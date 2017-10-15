package cn.bpm.core;
/**
 * 当抛出这个异常时，就会重定向到登录页面<br>
 * 通过以下的MVC配置实现<br>
 * <br>
 * <pre>
 * 	&lt;bean id="handlerExceptionResolver"
		class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver>
		&lt;property name="exceptionMappings">
			&lt;props>
				 &lt;!--以下异常为自定义异常，此异常直接继承Exception即可 -->
				&lt;prop key="cn.bpm.core.AuthException">redirect:/index.html&lt;/prop>
			&lt;/props>
		&lt;/property>
	&lt;/bean>
	<pre>
 */
public class AuthException extends Exception {
	private static final long serialVersionUID = 3040103859518323701L;
	
}
