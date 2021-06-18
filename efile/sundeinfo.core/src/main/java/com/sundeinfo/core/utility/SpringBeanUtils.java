package com.sundeinfo.core.utility;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringBeanUtils {
	
	 private static ApplicationContext applicationContext;

	 public static void setApplicationContext(ApplicationContext applicationContext) {
	      SpringBeanUtils.applicationContext = applicationContext;
	 }
	 
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz, String beanName) {
	        
	    ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	    if (context == null) {
	           context = applicationContext;
	    }
	    return (T) context.getBean(beanName);
	  }


}
