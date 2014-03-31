package com.aj.wagon;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceCollector extends Collector {

	private static final String NUMBER_DELIM = ":";

	private SharedPreferences preferences;

	public PreferenceCollector(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	@Override
	boolean collectString(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			editor.putString(key, (String) field.get(instance));
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
		return collectNumberAsString(field, key, instance, Long.class);
	}

	@Override
	boolean collectDouble(Field field, String key, Object instance) {
		return collectNumberAsString(field, key, instance, Double.class);
	}

	@Override
	boolean collectFloat(Field field, String key, Object instance) {
		return collectNumberAsString(field, key, instance, Float.class);
	}

	@Override
	boolean collectInt(Field field, String key, Object instance) {
		return collectNumberAsString(field, key, instance, Integer.class);
	}

	private Set<String> setFromList(ArrayList<String> list) {
		return new HashSet<String>(list);
	}

	private Editor getEditor() {
		return preferences.edit();
	}

	private boolean collectNumberAsString(Field field, String key, Object instance, Type type) {
		boolean itWorked = true;
		try {
			SharedPreferences.Editor editor = getEditor();
			editor.putString(key, type.getClass().getSimpleName() + NUMBER_DELIM + field.get(instance).toString());
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}
}
