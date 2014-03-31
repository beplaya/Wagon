package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

public class PreferenceExtractor<E> extends Extractor {

	@Override
	boolean extractArrayList(Object preferences, Field field, String key, Object instance, boolean itWorked) {
		try {
			Set<String> stringSet = ((SharedPreferences) preferences).getStringSet(key, new HashSet<String>());
			field.set(instance, new ArrayList<String>(stringSet));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractString(Object preferences, Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, ((SharedPreferences) preferences).getString(key, null));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractInt(Object preferences, Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, ((SharedPreferences) preferences).getInt(key, 0));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractFloat(Object preferences, Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, ((SharedPreferences) preferences).getFloat(key, 0));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	@Override
	boolean extractDouble(Object preferences, Field field, String key, Object instance, boolean itWorked) {
		try {
			String value = ((SharedPreferences) preferences).getString(key, null);
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
	boolean extractLong(Object preferences, Field field, String key, Object instance, boolean itWorked) {
		try {
			field.set(instance, ((SharedPreferences) preferences).getLong(key, 0));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

}
