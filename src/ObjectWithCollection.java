import java.util.List;

public class ObjectWithCollection {
    private List<SimpleObject> simpleObjectList;

    public ObjectWithCollection(){}
    // Constructors, getters, and setters
    public ObjectWithCollection(List<SimpleObject> simpleObjectList) {
        this.simpleObjectList = simpleObjectList;
    }

    public List<SimpleObject> getSimpleObjectList() {
        return simpleObjectList;
    }

    public void setSimpleObjectList(List<SimpleObject> simpleObjectList) {
        this.simpleObjectList = simpleObjectList;
    }
}
