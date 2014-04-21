package com.aj.wagon;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class NameValueCollector extends Collector {

	@Override
	boolean collectString(Object list, Field field, String key, Object instance) {
		return collectValue(list, field, key, instance);
	}

	@Override
	boolean collectArrayList(Object list, Field field, String key, Object instance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean collectLong(Object list, Field field, String key, Object instance) {
		return collectValue(list, field, key, instance);
	}

	@Override
	boolean collectDouble(Object list, Field field, String key, Object instance) {
		return collectValue(list, field, key, instance);
	}

	@Override
	boolean collectFloat(Object list, Field field, String key, Object instance) {
		return collectValue(list, field, key, instance);
	}

	private boolean collectValue(Object list, Field field, String key, Object instance) {
		return collectValue(list, field, key, instance);
	}

	@Override
	boolean collectInt(Object list, Field field, String key, Object instance) {
		try {
			((ArrayList<NameValuePair>) list).add(new BasicNameValuePair(key, (field.get(instance)).toString()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
