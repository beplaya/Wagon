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

				if (shouldPackField(annotation) || unpackAllFields) {
					if (annotation instanceof WoodBox || unpackAllFields) {
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
		}

		return itWorked;
	}

	private boolean upackBox(Bundle extras, Field field, Annotation annotation, String key, Object instance) {
		boolean itWorked = false;
		Class<?> type = field.getType();
		if (type.equals(ArrayList.class)) {
			itWorked = extractArrayList(extras, field, key, instance, itWorked);
		} else if (type.equals(String.class)) {
			itWorked = extractString(extras, field, key, instance, itWorked);
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			itWorked = extractInt(extras, field, key, instance, itWorked);
		} else if (type.equals(float.class) || type.equals(Float.class)) {
			itWorked = extractFloat(extras, field, key, instance, itWorked);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			itWorked = extractDouble(extras, field, key, instance, itWorked);
		} else if (type.equals(long.class) || type.equals(long.class)) {
			itWorked = extractLong(extras, field, key, instance, itWorked);
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
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			itWorked = collectInt(intent, field, key, instance);
		} else if (type.equals(float.class) || type.equals(Float.class)) {
			itWorked = collectFloat(intent, field, key, instance);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			itWorked = collectDouble(intent, field, key, instance);
		} else if (type.equals(long.class) || type.equals(long.class)) {
			itWorked = collectLong(intent, field, key, instance);
		}
		return itWorked;
	}

	private boolean collectLong(Intent intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			long v = (Long) field.get(instance);
			intent.putExtra(key, v);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean collectDouble(Intent intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			double v = (Double) field.get(instance);
			intent.putExtra(key, v);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean collectFloat(Intent intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			float v = (Float) field.get(instance);
			intent.putExtra(key, v);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean collectInt(Intent intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			int v = (Integer) field.get(instance);
			intent.putExtra(key, v);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
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

	private boolean extractArrayList(Bundle extras, Field field, String key, Object instance, boolean itWorked) {
		ArrayList<String> value = extras.getStringArrayList(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean extractString(Bundle extras, Field field, String key, Object instance, boolean itWorked) {
		String value = extras.getString(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean extractInt(Bundle extras, Field field, String key, Object instance, boolean itWorked) {
		int value = extras.getInt(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean extractFloat(Bundle extras, Field field, String key, Object instance, boolean itWorked) {
		float value = extras.getFloat(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean extractDouble(Bundle extras, Field field, String key, Object instance, boolean itWorked) {
		double value = extras.getDouble(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private boolean extractLong(Bundle extras, Field field, String key, Object instance, boolean itWorked) {
		long value = extras.getLong(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}
}
