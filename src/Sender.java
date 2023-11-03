import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
            ObjectCreator objectCreator = new ObjectCreator();
            ArrayList<Object> objects = objectCreator.createObject();

            // Serialize the object
            Serializer serializer = new Serializer();
            for (Object object : objects){
                Document document = serializer.serialize(object);
                XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
                String xmlString = xmlOutputter.outputString(document);
                out.writeObject(xmlString);

            }
            out.flush();
            out.close();
            socket.close();

            // Convert the JDOM document to a string


            // Send the serialized object to the Receiver


            System.out.println("Object sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
