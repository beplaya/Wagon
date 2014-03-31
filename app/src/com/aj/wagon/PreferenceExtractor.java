package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

public class PreferenceExtractor<E> extends Extractor {

	private SharedPreferences preferences;

	public PreferenceExtractor(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	@Override
	boolean extractArrayList(Field field, String key, Object instance, boolean itWorked) {
		try {
			Set<String> stringSet = preferences.getStringSet(key, new HashSet<String>());
			field.set(instance, new ArrayList<String>(stringSet));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractString(Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, preferences.getString(key, null));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractInt(Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, preferences.getInt(key, 0));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractFloat(Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, preferences.getFloat(key, 0));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractDouble(Field field, String key, Object instance, boolean itWorked) {
		try {
			String value = preferences.getString(key, null);
			String[] split = value.split(PreferenceCollector.KEY_DOUBLE);
			if (split[0].equalsIgnoreCase(Integer.class.getSimpleName())) {
				field.set(instance, Double.parseDouble(split[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractLong(Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, preferences.getLong(key, 0));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

}
