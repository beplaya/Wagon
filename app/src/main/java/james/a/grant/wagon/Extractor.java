package james.a.grant.wagon;

import java.lang.reflect.Field;

public abstract class Extractor {
	abstract boolean extractArrayList(Object data, Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractString(Object data, Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractInt(Object data, Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractFloat(Object data, Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractDouble(Object data, Field field, String key, Object instance, boolean itWorked);

	abstract boolean extractLong(Object data, Field field, String key, Object instance, boolean itWorked);
}
