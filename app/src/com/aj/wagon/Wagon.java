package com.aj.wagon;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Wagon<E> {

	public static final String VERSION = "1.02";
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
		ExtrasCollector collector = new ExtrasCollector(intent);
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(WoodBox.class);
			if (annotation == null)
				annotation = field.getAnnotation(Crate.class);
			if (shouldPackField(annotation) || packAllFields) {
				if (annotation instanceof Crate) {
					try {
						Object instanceOfCrate = field.get(instance);
						Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
						itWorked = pack(intent, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
					} catch (Exception e) {
						e.printStackTrace();
						itWorked = false;
					}
				} else if (annotation instanceof WoodBox || packAllFields) {
					String key = packAllFields ? crateKey + field.getName() : getKey(annotation);
					itWorked = gatherBoxes(collector, itWorked, field, annotation, key, instance);
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
		Extractor extractor = new ExtrasExtractor(extras);
		if (extras != null) {
			Field[] declaredFields = objTypeToPack.getDeclaredFields();
			for (Field field : declaredFields) {
				Annotation annotation = field.getAnnotation(WoodBox.class);
				if (annotation == null)
					annotation = field.getAnnotation(Crate.class);

				if (shouldPackField(annotation) || unpackAllFields) {
					if (annotation instanceof Crate) {
						try {
							Object instanceOfCrate = field.get(instance);
							Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
							itWorked = unpack(intent, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
						} catch (Exception e) {
							e.printStackTrace();
							itWorked = false;
						}
					} else if (annotation instanceof WoodBox || unpackAllFields) {
						String key = unpackAllFields ? crateKey + field.getName() : getKey(annotation);
						itWorked = upackBox(extractor, field, annotation, key, instance);
					}
				}
			}
		}

		return itWorked;
	}

	private boolean upackBox(Extractor extractor, Field field, Annotation annotation, String key, Object instance) {
		boolean itWorked = false;
		Class<?> type = field.getType();
		if (type.equals(ArrayList.class)) {
			itWorked = extractor.extractArrayList(field, key, instance, itWorked);
		} else if (type.equals(String.class)) {
			itWorked = extractor.extractString(field, key, instance, itWorked);
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			itWorked = extractor.extractInt(field, key, instance, itWorked);
		} else if (type.equals(float.class) || type.equals(Float.class)) {
			itWorked = extractor.extractFloat(field, key, instance, itWorked);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			itWorked = extractor.extractDouble(field, key, instance, itWorked);
		} else if (type.equals(long.class) || type.equals(long.class)) {
			itWorked = extractor.extractLong(field, key, instance, itWorked);
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

	private boolean gatherBoxes(Collector collector, boolean itWorked, Field field, Annotation annotation, String key, Object instance) {
		Class<?> type = field.getType();
		if (type.equals(ArrayList.class)) {
			itWorked = collector.collectArrayList(field, key, instance);
		} else if (type.equals(String.class)) {
			itWorked = collector.collectString(field, key, instance);
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			itWorked = collector.collectInt(field, key, instance);
		} else if (type.equals(float.class) || type.equals(Float.class)) {
			itWorked = collector.collectFloat(field, key, instance);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			itWorked = collector.collectDouble(field, key, instance);
		} else if (type.equals(long.class) || type.equals(long.class)) {
			itWorked = collector.collectLong(field, key, instance);
		}
		return itWorked;
	}

	public boolean save(SharedPreferences preferences, Class<? extends Object> objTypeToPack, Object instance, boolean packAllFields, String crateKey) {
		boolean itWorked = true;
		Field[] declaredFields = objTypeToPack.getDeclaredFields();
		Collector collector = new PreferenceCollector(preferences);
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(WoodBox.class);
			if (annotation == null)
				annotation = field.getAnnotation(Crate.class);
			if (shouldPackField(annotation) || packAllFields) {
				if (annotation instanceof Crate) {
					try {
						Object instanceOfCrate = field.get(instance);
						Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
						itWorked = save(preferences, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
					} catch (Exception e) {
						e.printStackTrace();
						itWorked = false;
					}
				} else if (annotation instanceof WoodBox || packAllFields) {
					String key = packAllFields ? crateKey + field.getName() : getKey(annotation);
					itWorked = gatherBoxes(collector, itWorked, field, annotation, key, instance);
				}
			}
		}

		return itWorked;
	}

	public boolean load(SharedPreferences preferences, Class<? extends Object> objTypeToPack, Object instance, boolean unpackAllFields, String crateKey) {
		boolean itWorked = true;
		Extractor extractor = new PreferenceExtractor<E>(preferences);
		Field[] declaredFields = objTypeToPack.getDeclaredFields();
		for (Field field : declaredFields) {
			Annotation annotation = field.getAnnotation(WoodBox.class);
			if (annotation == null)
				annotation = field.getAnnotation(Crate.class);

			if (shouldPackField(annotation) || unpackAllFields) {
				if (annotation instanceof Crate) {
					try {
						Object instanceOfCrate = field.get(instance);
						Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
						itWorked = load(preferences, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
					} catch (Exception e) {
						e.printStackTrace();
						itWorked = false;
					}
				} else if (annotation instanceof WoodBox || unpackAllFields) {
					String key = unpackAllFields ? crateKey + field.getName() : getKey(annotation);
					itWorked = upackBox(extractor, field, annotation, key, instance);
				}
			}
		}

		return itWorked;
	}
}
