package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author JAMESG-C
 * 
 * @param <E>
 *            ClassWithWoodBoxes
 */
public class Wagon<E> {

	private static final String KEY_TOKEN = "~!..~~";
	private Object obj;
	private Class<? extends E> objType;

	/**
	 * 
	 * @param objType
	 *            ClassWithWoodBoxes.getClass();
	 * @param obj
	 *            ClassWithWoodBoxes instance
	 */
	public Wagon(Class<? extends E> objType, Object obj) {
		this.objType = objType;
		this.obj = obj;
	}

	/**
	 * 
	 * @param intent
	 *            intent to put extras in
	 * @return
	 */
	public boolean pack(Intent intent) {
		boolean itWorked = true;

		Field[] declaredFields = objType.getDeclaredFields();
		for (Field field : declaredFields) {
			WoodBox annotation = field.getAnnotation(WoodBox.class);
			if (annotation == null) {
			} else {
				itWorked = gatherBoxes(intent, itWorked, field, annotation);
			}
		}

		return itWorked;
	}

	public boolean unpack(Intent intent) {
		boolean itWorked = true;
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Field[] declaredFields = objType.getDeclaredFields();
			for (Field field : declaredFields) {
				WoodBox annotation = field.getAnnotation(WoodBox.class);
				if (annotation == null) {
				} else {
					String key = annotation.key();
					Class<?> type = field.getType();
					if (type.equals(ArrayList.class)) {
						ArrayList<String> value = extras.getStringArrayList(key);
						try {
							field.set(obj, value);
						} catch (Exception e) {
							e.printStackTrace();
							itWorked = false;
						}
					} else if (type.equals(String.class)) {
						String value = extras.getString(key);
						try {
							field.set(obj, value);
						} catch (Exception e) {
							e.printStackTrace();
							itWorked = false;
						}
					}
				}
			}
		}

		return itWorked;
	}

	private boolean gatherBoxes(Intent intent, boolean itWorked, Field field, WoodBox annotation) {
		String key = annotation.key();
		Class<?> type = field.getType();
		if (type.equals(ArrayList.class)) {
			itWorked = collectArrayList(intent, field, key);
		} else if (type.equals(String.class)) {
			itWorked = collectString(intent, field, key);
		}
		return itWorked;
	}

	private boolean collectString(Intent intent, Field field, String key) {
		boolean itWorked = true;
		try {
			String alist = (String) field.get(obj);
			intent.putExtra(key, alist);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean collectArrayList(Intent intent, Field field, String key) {
		boolean itWorked = true;
		try {
			ArrayList<String> alist = (ArrayList<String>) field.get(obj);
			intent.putExtra(key, alist);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

}
