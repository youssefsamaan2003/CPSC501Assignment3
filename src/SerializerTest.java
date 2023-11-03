import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerializationTest {

    private Serializer serializer;
    private Deserializer deserializer;
    private XMLOutputter outputter;

    @BeforeEach
    void setUp() {
        serializer = new Serializer();
        deserializer = new Deserializer();
        outputter = new XMLOutputter();
    }

    @Test
    void testSerializeSimpleObject() throws IllegalAccessException {
        SimpleObject simpleObject = new SimpleObject(10, 20.5, true);
        Document document = serializer.serialize(simpleObject);
        assertNotNull(document);
        String xml = outputter.outputString(document);
        assertTrue(xml.contains("<object class=\"SimpleObject\""));
    }

    @Test
    void testDeserializeSimpleObject() throws Exception {
        SimpleObject simpleObject = new SimpleObject(10, 20.5, false);
        Document document = serializer.serialize(simpleObject);
        SimpleObject deserializedObject = (SimpleObject) deserializer.deserialize(document);
        assertNotNull(deserializedObject);
        assertEquals(simpleObject.getIntValue(), deserializedObject.getIntValue());
        assertEquals(simpleObject.getDoubleValue(), deserializedObject.getDoubleValue());
    }

    @Test
    void testSerializeObjectWithReferences() throws IllegalAccessException {
        ObjectWithReferences objectWithReferences = new ObjectWithReferences(new SimpleObject(10, 20.5, true), new ObjectWithPrimitiveArray());
        Document document = serializer.serialize(objectWithReferences);
        assertNotNull(document);
        String xml = outputter.outputString(document);
        assertTrue(xml.contains("<reference>"));
    }

    @Test
    void testDeserializeObjectWithReferences() throws Exception {
        ObjectWithReferences objectWithReferences = new ObjectWithReferences(new SimpleObject(10, 20.5, true), new ObjectWithPrimitiveArray());
        Document document = serializer.serialize(objectWithReferences);
        ObjectWithReferences deserializedObject = (ObjectWithReferences) deserializer.deserialize(document);
        assertNotNull(deserializedObject);
        assertNotNull(deserializedObject.getSimpleObject());
    }

    @Test
    void testSerializeArrayPrimitives() throws IllegalAccessException {
        int[] array = {1, 2, 3, 4, 5};
        Document document = serializer.serialize(array);
        assertNotNull(document);
        String xml = outputter.outputString(document);
        assertTrue(xml.contains("<object class=\"[I\""));
    }

    @Test
    void testDeserializeArrayPrimitives() throws Exception {
        int[] array = {1, 2, 3, 4, 5};
        Document document = serializer.serialize(array);
        int[] deserializedArray = (int[]) deserializer.deserialize(document);
        assertArrayEquals(array, deserializedArray);
    }

    @Test
    void testSerializeArrayOfReferences() throws IllegalAccessException {
        SimpleObject[] array = {
                new SimpleObject(1, 2.5, true),
                new SimpleObject(3, 4.5, false)
        };
        Document document = serializer.serialize(array);
        assertNotNull(document);
        String xml = outputter.outputString(document);
        assertTrue(xml.contains("<object class=\"[LSimpleObject;\""));
    }

    @Test
    void testDeserializeArrayOfReferences() throws Exception {
        SimpleObject[] array = {
                new SimpleObject(1, 2.5, true),
                new SimpleObject(3, 4.5, false)
        };
        Document document = serializer.serialize(array);
        SimpleObject[] deserializedArray = (SimpleObject[]) deserializer.deserialize(document);
        assertNotNull(deserializedArray);
        assertEquals(array.length, deserializedArray.length);
    }

    @Test
    void testSerializeObjectUsingCollection() throws IllegalAccessException {
        List<SimpleObject> list = new ArrayList<>();
        list.add(new SimpleObject(1, 2.5, true));
        list.add(new SimpleObject(3, 4.5, false));
        Document document = serializer.serialize(list);
        assertNotNull(document);
        String xml = outputter.outputString(document);
        assertTrue(xml.contains("<object class=\"java.util.ArrayList\""));
    }

    @Test
    void testDeserializeObjectUsingCollection() throws Exception {
        List<SimpleObject> list = new ArrayList<>();
        list.add(new SimpleObject(1,

                2.5, true));
        list.add(new SimpleObject(3, 4.5, false));
        Document document = serializer.serialize(list);
        List<SimpleObject> deserializedList = (List<SimpleObject>) deserializer.deserialize(document);
        assertNotNull(deserializedList);
        assertEquals(list.size(), deserializedList.size());
    }
}