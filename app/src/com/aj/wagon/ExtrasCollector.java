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
			String s = (String) field.get(instance);
			intent.putExtra(key, s);
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectArrayList(Field field, String key, Object instance) {
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

	boolean collectLong(Field field, String key, Object instance) {
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

	boolean collectDouble(Field field, String key, Object instance) {
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

	boolean collectFloat(Field field, String key, Object instance) {
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

	boolean collectInt(Field field, String key, Object instance) {
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
}
