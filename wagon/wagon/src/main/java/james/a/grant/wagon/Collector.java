package james.a.grant.wagon;

import java.lang.reflect.Field;

public abstract class Collector {
	abstract boolean collectString(Object data, Field field, String key, Object instance);

	abstract boolean collectArrayList(Object data, Field field, String key, Object instance);

	abstract boolean collectLong(Object data, Field field, String key, Object instance);

	abstract boolean collectDouble(Object data, Field field, String key, Object instance);

	abstract boolean collectFloat(Object data, Field field, String key, Object instance);

	abstract boolean collectInt(Object data, Field field, String key, Object instance);
}
