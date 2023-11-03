public class ObjectWithReferences {
    private SimpleObject simpleObject;
    private ObjectWithPrimitiveArray objectWithPrimitiveArray;

    public ObjectWithReferences(){}
    // Constructors, getters, and setters
    public ObjectWithReferences(SimpleObject simpleObject, ObjectWithPrimitiveArray objectWithPrimitiveArray){
        this.simpleObject = simpleObject;
        this.objectWithPrimitiveArray = objectWithPrimitiveArray;

    }

    public SimpleObject getSimpleObject() {
        return simpleObject;
    }

    public void setSimpleObject(SimpleObject simpleObject) {
        this.simpleObject = simpleObject;
    }
}
