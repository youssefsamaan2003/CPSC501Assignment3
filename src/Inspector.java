import java.lang.reflect.*;
import java.util.Arrays;

public class Inspector {

    public static void inspect(Object obj, boolean recursive) {
        Class<?> objClass = obj.getClass();
        // 1. The name of the declaring class
        System.out.println("Declaring Class: " + objClass.getName());

        // 2. The name of the immediate superclass
        Class<?> superClass = objClass.getSuperclass();
        if (superClass != null) {
            System.out.println("Immediate Superclass: " + superClass.getName());
        }

        // 3. The name of the interfaces the class implements
        Class<?>[] interfaces = objClass.getInterfaces();
        if (interfaces.length > 0) {
            System.out.println("Implemented Interfaces:");
            for (Class<?> intf : interfaces) {
                System.out.println(intf.getName());
            }
        }

        // 4. The methods the class declares
        Method[] methods = objClass.getDeclaredMethods();
        if (methods.length > 0) {
            System.out.println("Declared Methods:");
            for (Method method : methods) {
                System.out.println("Name: " + method.getName());
                System.out.println("Return Type: " + method.getReturnType().getName());
                System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
                System.out.println("Parameter Types: " + Arrays.toString(method.getParameterTypes()));
                System.out.println("Exceptions Thrown: " + Arrays.toString(method.getExceptionTypes()));
                System.out.println();
            }
        }

        // 5. The constructors the class declares
        Constructor<?>[] constructors = objClass.getDeclaredConstructors();
        if (constructors.length > 0) {
            System.out.println("Declared Constructors:");
            for (Constructor<?> constructor : constructors) {
                System.out.println("Parameter Types: " + Arrays.toString(constructor.getParameterTypes()));
                System.out.println("Modifiers: " + Modifier.toString(constructor.getModifiers()));
            }
        }

        // 6. The fields the class declares
        Field[] fields = objClass.getDeclaredFields();
        if (fields.length > 0) {
            System.out.println("Declared Fields:");
            for (Field field : fields) {
                System.out.println("Name: " + field.getName());
                System.out.println("Type: " + field.getType().getName());
                System.out.println("Modifiers: " + Modifier.toString(field.getModifiers()));

                Class<?> fieldType = field.getType();
                if (fieldType.isArray()) {
                    System.out.println("Field '" + field.getName() + "' is an Array");
                    System.out.println("Array Component Type: " + fieldType.getComponentType().getName());
                    field.setAccessible(true);
                    try {
                        Object fieldValue = field.get(obj);
                        if (fieldValue != null) {
                            System.out.println("Array Length: " + Array.getLength(fieldValue));
                            System.out.println("Array Contents:");
                            for (int i = 0; i < Array.getLength(fieldValue); i++) {
                                Object arrayElement = Array.get(fieldValue, i);
                                System.out.println("Element " + i + ":");
                                inspect(arrayElement, recursive);
                            }
                        } else {
                            System.out.println("Array is null");
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                if (recursive && !field.getType().isPrimitive()) {
                    try {
                        field.setAccessible(true);
                        Object fieldValue = field.get(obj);
                        if (fieldValue != null) {
                            // Recursively inspect the field if it's an object
                            inspect(fieldValue, true);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        field.setAccessible(true);
                        System.out.println("Value: " + field.get(obj));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
        }
        if (objClass.isArray()) {
            System.out.println("Array Component Type: " + objClass.getComponentType().getName());
            System.out.println("Array Length: " + Array.getLength(obj));
            System.out.println("Array Contents: " + Arrays.toString((Object[]) obj));
            for (int i = 0; i < Array.getLength(obj); i++) {
                Object arrayElement = Array.get(obj, i);
                System.out.println("Element " + i + ":");
                inspect(arrayElement, recursive);
            }
        }

    }
}
