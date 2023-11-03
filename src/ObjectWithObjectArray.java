public class ObjectWithObjectArray {
    private SimpleObject[] simpleObjectArray;

    // Constructors, getters, and setters
    public ObjectWithObjectArray(){}
    public ObjectWithObjectArray(SimpleObject[] simpleObjectArray){
        this.simpleObjectArray = simpleObjectArray;
    }

    public SimpleObject[] getSimpleObjectArray() {
        return simpleObjectArray;
    }

    public void setSimpleObjectArray(SimpleObject[] simpleObjectArray) {
        this.simpleObjectArray = simpleObjectArray;
    }
}
