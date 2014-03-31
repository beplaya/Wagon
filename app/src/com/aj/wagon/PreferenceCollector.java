package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceCollector extends Collector {

	public static final String DELIM = "$$@#_!:";
	public static final String KEY_DOUBLE = "KEY_DOUBLE";

	@Override
	boolean collectString(Object preferences, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor(preferences);
			String value = (String) field.get(instance);
			editor.putString(key, value);
			itWorked = editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectArrayList(Object preferences, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			Editor editor = getEditor(preferences);
			editor.putStringSet(key, new HashSet<String>((ArrayList<String>) field.get(instance)));
			itWorked = editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectLong(Object preferences, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor(preferences);
			editor.putLong(key, (Long) (field.get(instance)));
			itWorked = editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectDouble(Object preferences, Field field, String key, Object instance) {
		return collectDoubleAsString(getEditor(preferences), field, key, instance);
	}

	@Override
	boolean collectFloat(Object preferences, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor(preferences);
			editor.putFloat(key, (Float) (field.get(instance)));
			itWorked = editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectInt(Object preferences, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor(preferences);
			editor.putInt(key, (Integer) (field.get(instance)));
			itWorked = editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private Editor getEditor(Object preferences) {
		return ((SharedPreferences) preferences).edit();
	}

	private boolean collectDoubleAsString(SharedPreferences.Editor editor, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			editor.putString(key, KEY_DOUBLE + DELIM + ((Double) field.get(instance)));
			itWorked = editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}
}
