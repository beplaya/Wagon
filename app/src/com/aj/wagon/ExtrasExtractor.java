package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.os.Bundle;

public class ExtrasExtractor extends Extractor {
	private Bundle extras;

	public ExtrasExtractor(Bundle extras) {
		this.extras = extras;
	}

	boolean extractArrayList(Field field, String key, Object instance, boolean itWorked) {
		ArrayList<String> value = extras.getStringArrayList(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean extractString(Field field, String key, Object instance, boolean itWorked) {
		String value = extras.getString(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean extractInt(Field field, String key, Object instance, boolean itWorked) {
		int value = extras.getInt(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean extractFloat(Field field, String key, Object instance, boolean itWorked) {
		float value = extras.getFloat(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean extractDouble(Field field, String key, Object instance, boolean itWorked) {
		double value = extras.getDouble(key);
		try {
			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean extractLong(Field field, String key, Object instance, boolean itWorked) {
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
