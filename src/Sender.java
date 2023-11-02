import java.io.ObjectOutputStream;
import java.net.Socket;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Sender {
    public static void main(String[] args) {
        try {
            // Assume the Receiver program is running on localhost at port 1234
            Socket socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Create an object (for example, a SimpleObject)
            SimpleObject simpleObject = new SimpleObject(42, 3.14, true);

            // Serialize the object
            Serializer serializer = new Serializer();
            Document document = serializer.serialize(simpleObject);

            // Convert the JDOM document to a string
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            String xmlString = xmlOutputter.outputString(document);

            // Send the serialized object to the Receiver
            out.writeObject(xmlString);
            out.flush();
            out.close();
            socket.close();

            System.out.println("Object sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
