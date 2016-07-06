package james.a.grant.wagon;

import android.os.Bundle;

import java.lang.reflect.Field;


public class ExtrasExtractor extends Extractor {

    boolean extractArrayList(Object extras, Field field, String key, Object instance, boolean itWorked) {
        try {
            field.set(instance, ((Bundle) extras).getStringArrayList(key));
        } catch (Exception e) {
            e.printStackTrace();
            itWorked = false;
        }
        return itWorked;
    }

    boolean extractString(Object extras, Field field, String key, Object instance, boolean itWorked) {
        try {
            field.set(instance, ((Bundle) extras).getString(key));
        } catch (Exception e) {
            e.printStackTrace();
            itWorked = false;
        }
        return itWorked;
    }

    boolean extractInt(Object extras, Field field, String key, Object instance, boolean itWorked) {
        try {
            field.set(instance, ((Bundle) extras).getInt(key));
        } catch (Exception e) {
            e.printStackTrace();
            itWorked = false;
        }
        return itWorked;
    }

    boolean extractFloat(Object extras, Field field, String key, Object instance, boolean itWorked) {
        try {
            field.set(instance, ((Bundle) extras).getFloat(key));
        } catch (Exception e) {
            e.printStackTrace();
            itWorked = false;
        }
        return itWorked;
    }

    boolean extractDouble(Object extras, Field field, String key, Object instance, boolean itWorked) {
        try {
            field.set(instance, ((Bundle) extras).getDouble(key));
        } catch (Exception e) {
            e.printStackTrace();
            itWorked = false;
        }
        return itWorked;
    }

    boolean extractLong(Object extras, Field field, String key, Object instance, boolean itWorked) {
        try {
            field.set(instance, ((Bundle) extras).getLong(key));
        } catch (Exception e) {
            e.printStackTrace();
            itWorked = false;
        }
        return itWorked;
    }

}
