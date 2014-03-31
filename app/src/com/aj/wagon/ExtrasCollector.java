package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;

public class ExtrasCollector extends Collector {

	private Intent intent;

	public ExtrasCollector(Intent intent) {
		this.intent = intent;
	}

	boolean collectString(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			intent.putExtra(key, (String) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectArrayList(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			intent.putExtra(key, (ArrayList<String>) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectLong(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			intent.putExtra(key, (Long) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectDouble(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			intent.putExtra(key, (Double) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectFloat(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			intent.putExtra(key, (Float) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectInt(Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			intent.putExtra(key, (Integer) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}
}
