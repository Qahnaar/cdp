package com.markhyvka.proxy.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

public class ProxyUtil implements InvocationHandler {

	final static Logger LOG = Logger.getLogger(ProxyUtil.class);

	private static final String MONITORED_METHOD_NAME = "calculate";

	private Object object;

	public ProxyUtil(Object object) {
		this.object = object;
	}

	static public Object newInstance(Object obj, Class<?>[] interfaces) {
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				interfaces, new ProxyUtil(obj));
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (method.getName().endsWith(MONITORED_METHOD_NAME)) {
			LOG.debug("Monitored method has been captured");
		}
		return method.invoke(object, args);
	}

}
