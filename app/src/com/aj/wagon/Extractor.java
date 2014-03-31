package com.aj.wagon;

import java.lang.reflect.Field;

public abstract class Extractor {
	abstract boolean extractArrayList(Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractString(Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractInt(Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractFloat(Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractDouble(Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractLong(Field field, String key, Object instance, boolean itWorked);
}
