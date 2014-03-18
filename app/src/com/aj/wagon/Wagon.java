package com.aj.wagon;

import java.lang.annotation.Annotation;
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
		return pack(intent, objType, obj, false, null);
	}

	/**
	 * 
	 * @param intent
	 *            intent to put extras in
	 * @return
	 */
	public boolean pack(Intent intent, Class<? extends Object> objTypeToPack, Object instance, boolean packAllFields, String crateKey) {
		boolean itWorked = true;
		Field[] declaredFields = objTypeToPack.getDeclaredFields();
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(WoodBox.class);
			if (annotation == null)
				annotation = field.getAnnotation(Crate.class);
			if (shouldPackField(annotation) || packAllFields) {
				if (annotation instanceof WoodBox || packAllFields) {
					String key = packAllFields ? crateKey + field.getName() : getKey(annotation);
					itWorked = gatherBoxes(intent, itWorked, field, annotation, key, instance);
				} else if (annotation instanceof Crate) {
					try {
						Object instanceOfCrate = field.get(instance);
						Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
						itWorked = pack(intent, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
					} catch (Exception e) {
						e.printStackTrace();
						itWorked = false;
					}
				}
			}
		}

		return itWorked;
	}

	private boolean shouldPackField(Annotation annotation) {
		if (annotation == null)
			return false;
		return annotation instanceof WoodBox || annotation instanceof Crate;
	}

	public boolean unpack(Intent intent) {
		return unpack(intent, objType, obj, false, null);
	}

	public boolean unpack(Intent intent, Class<? extends Object> objTypeToPack, Object instance, boolean unpackAllFields, String crateKey) {
		boolean itWorked = true;
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Field[] declaredFields = objTypeToPack.getDeclaredFields();
			for (Field field : declaredFields) {
				Annotation annotation = field.getAnnotation(WoodBox.class);
				if (annotation == null)
					annotation = field.getAnnotation(Crate.class);
				if (annotation == null) {
				} else if (annotation instanceof WoodBox || unpackAllFields) {
					String key = unpackAllFields ? crateKey + field.getName() : getKey(annotation);
					itWorked = upackBox(extras, field, annotation, key, instance);
				} else if (annotation instanceof Crate) {
					try {
						Object instanceOfCrate = field.get(instance);
						Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
						itWorked = unpack(intent, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
					} catch (Exception e) {
						e.printStackTrace();
						itWorked = false;
					}
				}
			}
		}

		return itWorked;
	}

	private boolean upackBox(Bundle extras, Field field, Annotation annotation, String key, Object instance) {
		boolean itWorked = false;
		Class<?> type = field.getType();
		if (type.equals(ArrayList.class)) {
			ArrayList<String> value = extras.getStringArrayList(key);
			try {
				field.set(instance, value);
			} catch (Exception e) {
				e.printStackTrace();
				itWorked = false;
			}
		} else if (type.equals(String.class)) {
			String value = extras.getString(key);
			try {
				field.set(instance, value);
			} catch (Exception e) {
				e.printStackTrace();
				itWorked = false;
			}
		}
		return itWorked;
	}

	private String getKey(Annotation annotation) {
		String key = "";
		if (annotation instanceof WoodBox)
			key = ((WoodBox) annotation).key();
		if (annotation instanceof Crate)
			key = ((Crate) annotation).key();
		return key;
	}

	private boolean gatherBoxes(Intent intent, boolean itWorked, Field field, Annotation annotation, String key, Object instance) {
		Class<?> type = field.getType();
		if (type.equals(ArrayList.class)) {
			itWorked = collectArrayList(intent, field, key, instance);
		} else if (type.equals(String.class)) {
			itWorked = collectString(intent, field, key, instance);
		}
		return itWorked;
	}

	private boolean collectString(Intent intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			String s = (String) field.get(instance);
			intent.putExtra(key, s);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean collectArrayList(Intent intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			ArrayList<String> alist = (ArrayList<String>) field.get(instance);
			intent.putExtra(key, alist);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

}
