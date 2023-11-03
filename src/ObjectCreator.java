import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjectCreator {
    private static final Scanner scanner = new Scanner(System.in);

    public ArrayList<Object> createObject() {
        boolean running = true;
        ArrayList<Object> createdObjects = new ArrayList<>();

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1: Create a SimpleObject");
            System.out.println("2: Create an ObjectWithReferences");
            System.out.println("3: Create an ObjectWithPrimitiveArray");
            System.out.println("4: Create an ObjectWithObjectArray");
            System.out.println("5: Create an ObjectWithCollection");
            System.out.println("6: Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createdObjects.add(createSimpleObject());
                    break;
                case 2:
                    createdObjects.add(createObjectWithReferences());
                    break;
                case 3:
                    createdObjects.add(createObjectWithPrimitiveArray());
                    break;
                case 4:
                    createdObjects.add(createObjectWithObjectArray());
                    break;
                case 5:
                    createdObjects.add(createObjectWithCollection());
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        System.out.println("Created objects:");
        for (Object obj : createdObjects) {
            System.out.println(obj);
        }

        return createdObjects;
    }

    private static SimpleObject createSimpleObject() {
        System.out.println("Creating a SimpleObject...");
        System.out.print("Enter an integer value: ");
        int intValue = scanner.nextInt();
        System.out.print("Enter a double value: ");
        double doubleValue = scanner.nextDouble();
        System.out.print("Enter a boolean value: ");
        boolean booleanValue = scanner.nextBoolean();
        return new SimpleObject(intValue, doubleValue, booleanValue);
    }


    private static ObjectWithReferences createObjectWithReferences() {
        System.out.println("Creating an ObjectWithReferences...");
        SimpleObject simpleObject = createSimpleObject();
        ObjectWithPrimitiveArray objectWithPrimitiveArray = createObjectWithPrimitiveArray();
        return new ObjectWithReferences(simpleObject, objectWithPrimitiveArray);
    }


    private static ObjectWithPrimitiveArray createObjectWithPrimitiveArray() {
        System.out.println("Creating an ObjectWithPrimitiveArray...");
        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            System.out.print("Enter value for element " + i + ": ");
            array[i] = scanner.nextInt();
        }
        return new ObjectWithPrimitiveArray(array);
    }


    private static ObjectWithObjectArray createObjectWithObjectArray() {
        System.out.println("Creating an ObjectWithObjectArray...");
        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();
        SimpleObject[] array = new SimpleObject[size];
        for (int i = 0; i < size; i++) {
            array[i] = createSimpleObject();
        }
        return new ObjectWithObjectArray(array);
    }


    private static ObjectWithCollection createObjectWithCollection() {
        System.out.println("Creating an ObjectWithCollection...");
        System.out.print("Enter the number of elements in the collection: ");
        int size = scanner.nextInt();
        List<SimpleObject> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(createSimpleObject());
        }
        return new ObjectWithCollection(list);
    }

}
