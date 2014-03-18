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
		return pack(intent, objType, obj, false);
	}

	/**
	 * 
	 * @param intent
	 *            intent to put extras in
	 * @return
	 */
	public boolean pack(Intent intent, Class<? extends Object> objTypeToPack, Object instance) {
		return pack(intent, objTypeToPack, instance, false);
	}

	/**
	 * 
	 * @param intent
	 *            intent to put extras in
	 * @return
	 */
	public boolean pack(Intent intent, Class<? extends Object> objTypeToPack, Object instance, boolean packAllFields) {
		boolean itWorked = true;

		Field[] declaredFields = objTypeToPack.getDeclaredFields();
		for (Field field : declaredFields) {
			WoodBox annotation = field.getAnnotation(WoodBox.class);
			if (shouldPackField(annotation) || packAllFields) {
				itWorked = gatherBoxes(intent, itWorked, field, annotation);
			} else if (annotation instanceof Crate) {
				try {
					Object instanceOfCrate = field.get(instance);
					Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
					pack(intent, objTypeOfCrate, instanceOfCrate, true);
				} catch (Exception e) {
					e.printStackTrace();
					itWorked = false;
				}
				return itWorked;
			}
		}

		return itWorked;
	}

	private boolean shouldPackField(WoodBox annotation) {
		if (annotation == null)
			return false;
		return annotation instanceof WoodBox;
	}

	public boolean unpack(Intent intent) {
		boolean itWorked = true;
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Field[] declaredFields = objType.getDeclaredFields();
			for (Field field : declaredFields) {
				WoodBox annotation = field.getAnnotation(WoodBox.class);
				if (annotation == null) {
				} else if (annotation instanceof WoodBox) {
					itWorked = upackBox(extras, field, annotation);
				} else if (annotation instanceof Crate) {

				}
			}
		}

		return itWorked;
	}

	private boolean upackBox(Bundle extras, Field field, WoodBox annotation) {
		boolean itWorked = false;
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
			String s = (String) field.get(obj);
			intent.putExtra(key, s);
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
