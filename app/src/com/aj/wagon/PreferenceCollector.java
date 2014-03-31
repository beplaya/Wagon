package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceCollector extends Collector {

	public static final String NUMBER_DELIM = ":";
	public static final String KEY_DOUBLE = "KEY_DOUBLE";

	private SharedPreferences preferences;

	public PreferenceCollector(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	@Override
	boolean collectString(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			String value = (String) field.get(instance);
			System.out.println("##############" + value);
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectArrayList(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			Editor editor = getEditor();
			editor.putStringSet(key, setFromList((ArrayList<String>) field.get(instance)));

		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectLong(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			editor.putLong(key, (Long) (field.get(instance)));
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectDouble(Field field, String key, Object instance) {
		return collectDoubleAsString(field, key, instance);
	}

	@Override
	boolean collectFloat(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			editor.putFloat(key, (Float) (field.get(instance)));
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean collectInt(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			editor.putInt(key, (Integer) (field.get(instance)));
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	private Set<String> setFromList(ArrayList<String> list) {
		return new HashSet<String>(list);
	}

	private Editor getEditor() {
		return preferences.edit();
	}

	private boolean collectDoubleAsString(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			editor.putString(key, KEY_DOUBLE + NUMBER_DELIM + ((Double) field.get(instance)));
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}
}
