import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class Deserializer {
    private HashMap<String, Object> objectMap = new HashMap<>();

    public Object deserialize(Document document) throws Exception {
        List<Element> objectElements = document.getRootElement().getChildren();
        createInstances(objectElements);
        assignFieldValues(objectElements);
        return objectMap.get("0");
    }

    private void createInstances(List<Element> objectElements) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Element objectElement : objectElements) {
            Class<?> clazz = Class.forName(objectElement.getAttributeValue("class"));
            Object instance;
            if (clazz.isArray()) {
                int length = Integer.parseInt(objectElement.getAttributeValue("length"));
                instance = Array.newInstance(clazz.getComponentType(), length);
            } else {
                instance = clazz.getConstructor().newInstance();
            }
            String id = objectElement.getAttributeValue("id");
            objectMap.put(id, instance);
        }
    }

    private void assignFieldValues(List<Element> objectElements) throws Exception {
        for (Element objectElement : objectElements) {
            Object instance = objectMap.get(objectElement.getAttributeValue("id"));
            List<Element> fieldElements = objectElement.getChildren("field");
            for (Element fieldElement : fieldElements) {
                String className = fieldElement.getAttributeValue("declaringclass");
                Class<?> fieldDeclaringClass = Class.forName(className);
                String fieldName = fieldElement.getAttributeValue("name");
                Field field = fieldDeclaringClass.getDeclaredField(fieldName);
                field.setAccessible(true);

                Element valueElement = fieldElement.getChild("value");
                if (valueElement != null) {
                    Object value = parseValue(field.getType(), valueElement.getText());
                    field.set(instance, value);
                } else {
                    Element referenceElement = fieldElement.getChild("reference");
                    if (referenceElement != null) {
                        Object referencedInstance = objectMap.get(referenceElement.getText());
                        field.set(instance, referencedInstance);
                    }
                }
            }
        }
    }

    private Object parseValue(Class<?> fieldType, String value) {
        if (fieldType.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (fieldType.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (fieldType.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldType.equals(char.class)) {
            return value.charAt(0);
        } else if (fieldType.equals(byte.class)) {
            return Byte.parseByte(value);
        } else if (fieldType.equals(short.class)) {
            return Short.parseShort(value);
        } else if (fieldType.equals(long.class)) {
            return Long.parseLong(value);
        } else if (fieldType.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (fieldType.equals(String.class)) {
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }
    }
}

