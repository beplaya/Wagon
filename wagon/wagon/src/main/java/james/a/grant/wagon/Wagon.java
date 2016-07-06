package james.a.grant.wagon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Wagon<E> {

    public static final String VERSION = "1.10";
    private Object obj;
    private Class<? extends E> objType;

    /**
     * @param objType ClassWithWoodBoxes.getClass();
     * @param obj     ClassWithWoodBoxes instance
     */
    public Wagon(Class<? extends E> objType, Object obj) {
        this.objType = objType;
        this.obj = obj;
    }

    /**
     * @param intent intent to put extras in
     * @return
     */
    public boolean pack(Intent intent) {
        return pack(intent, objType, obj, false, null);
    }

    /**
     * @param intent intent to put extras in
     * @return
     */
    public boolean pack(Intent intent, Class<? extends Object> objTypeToPack, Object instance, boolean packAllFields, String crateKey) {
        boolean itWorked = true;
        Field[] declaredFields = objTypeToPack.getDeclaredFields();
        Collector collector = new ExtrasCollector();
        for (Field field : declaredFields) {
            Annotation annotation = getDataAnnotation(field);
            annotation = getAnnotationIfCrateOrNested(field, annotation);
            if (isAPackableField(annotation) || packAllFields) {
                if (isCrateOrNestedData(annotation)) {
                    try {
                        Object instanceOfCrate = field.get(instance);
                        Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
                        itWorked = pack(intent, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
                    } catch (Exception e) {
                        e.printStackTrace();
                        itWorked = false;
                    }
                } else if (isWoodBoxOrData(annotation) || packAllFields) {
                    String key = packAllFields ? crateKey + field.getName() : getKey(annotation);
                    itWorked = gatherBoxes(intent, collector, itWorked, field, key, instance);
                }
            }
        }

        return itWorked;
    }

    private Annotation getDataAnnotation(Field field) {
        Annotation annotation = field.getAnnotation(WoodBox.class);
        annotation = annotation == null ? field.getAnnotation(Data.class) : null;
        return annotation;
    }

    private boolean isCrateOrNestedData(Annotation annotation) {
        return annotation instanceof Crate || annotation instanceof NestedData;
    }

    private boolean isAPackableField(Annotation annotation) {
        if (annotation == null)
            return false;
        return isWoodBoxOrData(annotation) || isCrateOrNestedData(annotation);
    }

    public boolean unpack(Intent intent) {
        return unpack(intent, objType, obj, false, null);
    }

    public boolean unpack(Intent intent, Class<? extends Object> objTypeToPack, Object instance,
                          boolean unpackAllFields, String crateKey) {
        boolean itWorked = true;
        Bundle extras = intent.getExtras();
        Extractor extractor = new ExtrasExtractor();
        if (extras != null) {
            Field[] declaredFields = objTypeToPack.getDeclaredFields();
            for (Field field : declaredFields) {
                Annotation annotation = getDataAnnotation(field);
                annotation = getAnnotationIfCrateOrNested(field, annotation);
                if (isAPackableField(annotation) || unpackAllFields) {
                    if (isCrateOrNestedData(annotation)) {
                        try {
                            Object instanceOfCrate = field.get(instance);
                            Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
                            itWorked = unpack(intent, objTypeOfCrate, instanceOfCrate, true, getKey(annotation));
                        } catch (Exception e) {
                            e.printStackTrace();
                            itWorked = false;
                        }
                    } else if (isWoodBoxOrData(annotation) || unpackAllFields) {
                        String key = unpackAllFields ? crateKey + field.getName() : getKey(annotation);
                        itWorked = unpackBox(extras, extractor, field, key, instance, itWorked);
                    }
                }
            }
        }

        return itWorked;
    }

    private boolean unpackBox(Object data, Extractor extractor, Field field, String key, Object instance, boolean itWorked) {
        Class<?> type = field.getType();
        if (type.equals(ArrayList.class)) {
            itWorked = extractor.extractArrayList(data, field, key, instance, itWorked);
        } else if (type.equals(String.class)) {
            itWorked = extractor.extractString(data, field, key, instance, itWorked);
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            itWorked = extractor.extractInt(data, field, key, instance, itWorked);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            itWorked = extractor.extractFloat(data, field, key, instance, itWorked);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            itWorked = extractor.extractDouble(data, field, key, instance, itWorked);
        } else if (type.equals(long.class) || type.equals(long.class)) {
            itWorked = extractor.extractLong(data, field, key, instance, itWorked);
        }
        return itWorked;
    }

    private String getKey(Annotation annotation) {
        String key = "";
        if (annotation instanceof WoodBox) {
            key = ((WoodBox) annotation).key();
        } else if (annotation instanceof Data) {
            key = ((Data) annotation).key();
        } else if (annotation instanceof Crate) {
            key = ((Crate) annotation).key();
        } else if (annotation instanceof NestedData) {
            key = ((NestedData) annotation).key();
        }
        return key;
    }

    private boolean gatherBoxes(Object data, Collector collector, boolean itWorked,
                                Field field, String key, Object instance) {
        Class<?> type = field.getType();
        if (type.equals(ArrayList.class)) {
            itWorked = collector.collectArrayList(data, field, key, instance);
        } else if (type.equals(String.class)) {
            itWorked = collector.collectString(data, field, key, instance);
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            itWorked = collector.collectInt(data, field, key, instance);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            itWorked = collector.collectFloat(data, field, key, instance);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            itWorked = collector.collectDouble(data, field, key, instance);
        } else if (type.equals(long.class) || type.equals(long.class)) {
            itWorked = collector.collectLong(data, field, key, instance);
        }
        return itWorked;
    }

    public boolean pack(SharedPreferences preferences) {
        return pack(preferences, objType, obj, false, null, 0);
    }

    public boolean pack(SharedPreferences preferences, Class<? extends Object> objTypeToPack,

                        Object instance, boolean packAllFields, String crateKey, int level) {
        boolean itWorked = true;
        Field[] declaredFields = objTypeToPack.getDeclaredFields();
        Collector collector = new PreferenceCollector();
        for (Field field : declaredFields) {
            Annotation annotation = getDataAnnotation(field);
            annotation = getAnnotationIfCrateOrNested(field, annotation);


            if (isPackablePreference(level, annotation)) {
                if (isAPackableField(annotation) || packAllFields) {
                    if (annotation instanceof Crate) {
                        try {
                            Object instanceOfCrate = field.get(instance);
                            Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
                            itWorked = pack(preferences, objTypeOfCrate, instanceOfCrate, true, getKey(annotation), level + 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            itWorked = false;
                        }
                    } else if (isWoodBoxOrData(annotation) || packAllFields) {
                        String key = packAllFields ? crateKey + field.getName() : getKey(annotation);
                        itWorked = gatherBoxes(preferences, collector, itWorked, field, key, instance);
                    }
                }
            }
        }

        return itWorked;
    }

    private Annotation getAnnotationIfCrateOrNested(Field field, Annotation annotation) {
        if (annotation == null) {
            annotation = field.getAnnotation(Crate.class);
        }
        if (annotation == null) {
            annotation = field.getAnnotation(NestedData.class);
        }
        return annotation;
    }

    private boolean isPackablePreference(int level, Annotation annotation) {
        boolean shouldPackPreference = false;
        if (level == 0) {
            if (annotation != null) {
                if (annotation instanceof Crate) {
                    shouldPackPreference = ((Crate) annotation).preference();
                } else if (annotation instanceof NestedData) {
                    shouldPackPreference = ((NestedData) annotation).preference();
                } else if (annotation instanceof WoodBox) {
                    shouldPackPreference = ((WoodBox) annotation).preference();
                } else if (annotation instanceof Data) {
                    shouldPackPreference = ((Data) annotation).preference();
                }
            }
        } else {
            shouldPackPreference = true;
        }
        return shouldPackPreference;
    }

    public boolean unpack(SharedPreferences preferences) {
        return unpack(preferences, objType, obj, false, null, 0);
    }

    public boolean unpack(SharedPreferences preferences, Class<? extends Object> objTypeToPack,
                          Object instance, boolean unpackAllFields, String crateKey, int level) {
        boolean itWorked = true;
        Extractor extractor = new PreferenceExtractor<E>();
        Field[] declaredFields = objTypeToPack.getDeclaredFields();
        for (Field field : declaredFields) {
            Annotation annotation = getDataAnnotation(field);
            annotation = getAnnotationIfCrateOrNested(field, annotation);
            if (isPackablePreference(level, annotation)) {
                if (isAPackableField(annotation) || unpackAllFields) {
                    if (isCrateOrNestedData(annotation)) {
                        try {
                            Object instanceOfCrate = field.get(instance);
                            Class<? extends Object> objTypeOfCrate = instanceOfCrate.getClass();
                            itWorked = unpack(preferences, objTypeOfCrate, instanceOfCrate, true, getKey(annotation), level + 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            itWorked = false;
                        }
                    } else if (isWoodBoxOrData(annotation) || unpackAllFields) {
                        String key = unpackAllFields ? crateKey + field.getName() : getKey(annotation);
                        itWorked = unpackBox(preferences, extractor, field, key, instance, itWorked);
                    }
                }
            }
        }

        return itWorked;
    }

    private boolean isWoodBoxOrData(Annotation annotation) {
        return annotation instanceof WoodBox || annotation instanceof Data;
    }
}
