package com.aj.wagon;

import java.lang.reflect.Field;

public class PreferenceCollector extends Collector {

	@Override
	boolean collectString(Field field, String key, Object instance) {

		return false;
	}

	@Override
	boolean collectArrayList(Field field, String key, Object instance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean collectLong(Field field, String key, Object instance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean collectDouble(Field field, String key, Object instance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean collectFloat(Field field, String key, Object instance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean collectInt(Field field, String key, Object instance) {
		// TODO Auto-generated method stub
		return false;
	}

}
