package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;

public class ExtrasCollector extends Collector {

	boolean collectString(Object intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			((Intent) intent).putExtra(key, (String) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectArrayList(Object intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			((Intent) intent).putExtra(key, (ArrayList<String>) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectLong(Object intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			((Intent) intent).putExtra(key, (Long) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectDouble(Object intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			((Intent) intent).putExtra(key, (Double) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectFloat(Object intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			((Intent) intent).putExtra(key, (Float) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}

	boolean collectInt(Object intent, Field field, String key, Object instance) {
		boolean itWorked = true;
		try {
			((Intent) intent).putExtra(key, (Integer) field.get(instance));
		} catch (Exception e) {
			e.printStackTrace();
			itWorked = false;
		}
		return itWorked;
	}
}
