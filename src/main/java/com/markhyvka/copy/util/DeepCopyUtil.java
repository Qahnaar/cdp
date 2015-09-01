package com.markhyvka.copy.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeepCopyUtil {

	private final static Logger LOG = LoggerFactory
			.getLogger(DeepCopyUtil.class);

	public Object deepCopy(Object source) throws InstantiationException,
			IllegalAccessException {
		Class<?> sourceClass = source.getClass();
		Object target = sourceClass.newInstance();
		do {
			Field[] declaredFields = sourceClass.getDeclaredFields();
			LOG.debug("Started copying of " + sourceClass.getCanonicalName());
			deepCopyFields(source, target, declaredFields);
			sourceClass = sourceClass.getSuperclass();
		} while (sourceClass != Object.class);

		return target;
	}

	public void deepCopyFields(Object source, Object target, Field[] fields)
			throws InstantiationException, IllegalAccessException {

		for (Field field : fields) {
			if (!isFieldStatic(field)) {
				accessibilityCheck(field);
				Object fieldValue = field.get(source);
				LOG.debug("Copying field " + field.getName());
				if (field.getType().isPrimitive()) {
					field.set(target, fieldValue);
				} else {
					// TODO: here should be the proper implementation of deep
					// copy of the object for the testing purposes shallow copy
					// is performed
					field.set(target, fieldValue);
				}
			}
		}
	}

	private void accessibilityCheck(Field field) {
		if (!field.isAccessible()) {
			field.setAccessible(Boolean.TRUE);
		}
	}

	private boolean isFieldStatic(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}
}
