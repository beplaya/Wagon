package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;

public class Collector {
	boolean collectString(Intent intent, Field field, String key, Object instance) {
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

	boolean collectArrayList(Intent intent, Field field, String key, Object instance) {
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

	boolean collectLong(Intent intent, Field field, String key, Object instance) {
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

	boolean collectDouble(Intent intent, Field field, String key, Object instance) {
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

	boolean collectFloat(Intent intent, Field field, String key, Object instance) {
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

	boolean collectInt(Intent intent, Field field, String key, Object instance) {
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
