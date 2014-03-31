package com.aj.wagon;

import java.lang.reflect.Field;

public abstract class Collector {
	abstract boolean collectString(Field field, String key, Object instance);

	abstract boolean collectArrayList(Field field, String key, Object instance);

	abstract boolean collectLong(Field field, String key, Object instance);

	abstract boolean collectDouble(Field field, String key, Object instance);

	abstract boolean collectFloat(Field field, String key, Object instance);

	abstract boolean collectInt(Field field, String key, Object instance);
}
