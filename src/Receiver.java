import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import java.io.StringReader;

public class Receiver {
    public static void main(String[] args) {
        try {
            // Set up a ServerSocket to listen on port 1234
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Receiver is running and waiting for connections...");

            // Wait for a connection from the Sender
            Socket socket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Receive the serialized object as a string
            String xmlString = (String) in.readObject();
            System.out.println("Received object: \n" + xmlString);

            // Convert the string back to a JDOM document
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new StringReader(xmlString));

            // Deserialize the JDOM document back into an object
            Deserializer deserializer = new Deserializer();
            Object deserializedObject = deserializer.deserialize(document);

            ObjectVisualizer visualizer = new ObjectVisualizer();
            visualizer.visualize(deserializedObject);
            Inspector.inspect(deserializedObject, false);


            // Display the deserialized object
            System.out.println("Deserialized object: " + deserializedObject);
            System.out.println("Inspection Over.");
            // Close the connections
            in.close();
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
