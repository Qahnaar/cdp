package com.markhyvka.util;

import java.lang.reflect.Field;

public class DeepCopyUtil {

	public Object deepCopy(Object source) throws InstantiationException,
			IllegalAccessException {
		Class<?> sourceClass = source.getClass();
		Object target = sourceClass.newInstance();
		do {
			Field[] declaredFields = sourceClass.getDeclaredFields();
			deepCopyFields(source, target, declaredFields);
			sourceClass = sourceClass.getSuperclass();
		} while (sourceClass != Object.class);

		return target;
	}

	public void deepCopyFields(Object source, Object target, Field[] fields)
			throws InstantiationException, IllegalAccessException {

		for (Field field : fields) {
			accessibilityCheck(field);
			Object fieldValue = field.get(source);
			if (field.getType().isPrimitive()) {
				field.set(target, fieldValue);
			} else {
				// field.set(target, deepCopy(fieldValue));
			}
		}
	}

	private void accessibilityCheck(Field field) {
		if (!field.isAccessible()) {
			field.setAccessible(Boolean.TRUE);
		}
	}
}
