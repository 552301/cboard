package org.cboard.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * spring上下文工具类<br>
 * 参考 https://www.cnblogs.com/yjbjingcha/p/6752265.html
 * 
 * @author lu.wang
 *
 */
public class SpringContextUtil {

	/**
	 * 获取ApplicationContext
	 * 
	 * @return ApplicationContext
	 */
	private static ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(ContextLoader.getCurrentWebApplicationContext().getServletContext());
	}

	/**
	 * 获取bean。其他注释请参考 {@link BeanFactory}中的getBean()方法
	 * 
	 * @param requiredType
	 *            需要匹配的类型
	 * @return bean的实例与所需的类型匹配
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return getApplicationContext().getBean(requiredType);
	}
}
