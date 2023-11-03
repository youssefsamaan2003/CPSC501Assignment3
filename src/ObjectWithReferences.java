public class ObjectWithReferences {
    private SimpleObject simpleObject;
    private ObjectWithPrimitiveArray objectWithPrimitiveArray;

    public ObjectWithReferences(){}
    // Constructors, getters, and setters
    public ObjectWithReferences(SimpleObject simpleObject, ObjectWithPrimitiveArray objectWithPrimitiveArray){
        this.simpleObject = simpleObject;
        this.objectWithPrimitiveArray = objectWithPrimitiveArray;

    }
}
