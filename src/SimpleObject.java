public class SimpleObject {
    private int intValue;
    private double doubleValue;
    private boolean booleanValue;

    public SimpleObject(){}
    // Constructors, getters, and setters
    public SimpleObject(int intValue, double doubleValue, boolean booleanValue){
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
    }


    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }
}
