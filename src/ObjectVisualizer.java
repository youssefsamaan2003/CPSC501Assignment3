import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.IdentityHashMap;

public class ObjectVisualizer {

    private IdentityHashMap<Object, Integer> visitedObjects = new IdentityHashMap<>();
    private int objectCounter = 0;

    public void visualize(Object object) {
        if (object == null) {
            System.out.println("null");
            return;
        }
        try {
            visualizeObject(object, 0);
        } catch (IllegalAccessException e) {
            System.out.println("Error during object visualization: " + e.getMessage());
        }
    }

    private void visualizeObject(Object object, int indentLevel) throws IllegalAccessException {
        // Logging to check if the object has already been visited
        System.out.println(indent(indentLevel) + "Visiting object: " + System.identityHashCode(object));

        if (visitedObjects.containsKey(object)) {
            System.out.println(indent(indentLevel) + "[Circular reference to object #" + visitedObjects.get(object) + "]");
            return;
        }

        visitedObjects.put(object, objectCounter++);
        Class<?> objectClass = object.getClass();

        // Diagnostic print
        System.out.println(indent(indentLevel) + "Object class: " + object.getClass().getName() +
                " | Identity hash: " + System.identityHashCode(object) +
                " | Visited: " + visitedObjects.containsKey(object));

        if (visitedObjects.containsKey(object)) {
            System.out.println(indent(indentLevel) + "[Circular reference to object #" + visitedObjects.get(object) + "]");
            return;
        }

        visitedObjects.put(object, objectCounter++);


        // If it's a collection, array, or a class you've defined, proceed with field visualization.
        if (!objectClass.isPrimitive() && !objectClass.equals(String.class) &&
                (objectClass.isArray() || Collection.class.isAssignableFrom(objectClass) || objectClass.getPackageName().startsWith("your.package.name"))) {
            System.out.println(indent(indentLevel) + objectClass.getName() + " Object: ");
            for (Field field : objectClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                System.out.println(indent(indentLevel + 1) + field.getName() + ": ");
                visualizeObject(value, indentLevel + 2);
            }
        } else {
            // For objects that are not user-defined, simply print their toString representation.
            System.out.println(indent(indentLevel) + object.toString());
        }
        // Print simple representation for primitive types and strings
        if (objectClass.isPrimitive() || objectClass.equals(String.class)) {
            System.out.println(indent(indentLevel) + object);
        } else if (objectClass.isArray()) {
            System.out.println(indent(indentLevel) + objectClass.getComponentType() + " Array: ");
            for (int i = 0; i < Array.getLength(object); i++) {
                visualizeObject(Array.get(object, i), indentLevel + 1);
            }
        } else if (Collection.class.isAssignableFrom(objectClass)) {
            System.out.println(indent(indentLevel) + "Collection: ");
            for (Object item : (Collection<?>) object) {
                visualizeObject(item, indentLevel + 1);
            }
        } else {
            // Print fields for object types
            System.out.println(indent(indentLevel) + objectClass.getName() + " Object: ");
            for (Field field : objectClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                System.out.println(indent(indentLevel + 1) + field.getName() + ": ");
                visualizeObject(value, indentLevel + 2);
            }
        }
    }

    private String indent(int level) {
        return " ".repeat(level * 4);
    }
}
