import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;

public class Serializer {
    private IdentityHashMap<Object, String> objectMap = new IdentityHashMap<>();
    private int idCounter = 0;

    public Document serialize(Object obj) throws IllegalAccessException {
        Document document = new Document(new Element("serialized"));
        serializeObject(obj, document.getRootElement());
        return document;
    }

    private void serializeObject(Object obj, Element parentElement) throws IllegalAccessException, InaccessibleObjectException {
        // Check if object is already serialized
        if (objectMap.containsKey(obj)) {
            parentElement.addContent(new Element("reference").setText(objectMap.get(obj)));
            return;
        }

        // Assign a unique identifier to the object
        String id = String.valueOf(idCounter++);
        objectMap.put(obj, id);

        // Create an element for the object
        Element objectElement = new Element("object");
        objectElement.setAttribute("class", obj.getClass().getName());
        objectElement.setAttribute("id", id);
        parentElement.addContent(objectElement);

        // Serialize fields of the object
        for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field1 : fields) {
                // Skip static and final fields
                if (Modifier.isStatic(field1.getModifiers()) || Modifier.isFinal(field1.getModifiers())) {
                    continue;
                }
            }
            Element fieldElement = new Element("field");
            fieldElement.setAttribute("name", field.getName());
            fieldElement.setAttribute("declaringclass", field.getDeclaringClass().getName());
            objectElement.addContent(fieldElement);

            Object fieldValue = field.get(obj);
            if (fieldValue == null) {
                fieldElement.addContent(new Element("null"));
            } else if (field.getType().isPrimitive()) {
                fieldElement.addContent(new Element("value").setText(fieldValue.toString()));
            } else {
                serializeObject(fieldValue, fieldElement);
            }
        }
    }
}
