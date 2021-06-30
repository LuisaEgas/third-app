package aws.example.s3;

import java.util.List;

public class Example {

    public static void main(String[] args) {

        List<String> messages = new MessagesSQS().ExampleSQS();
        if (messages.isEmpty()) {
            System.out.println("No hay mensajes en la cola");
        }
        else {
            System.out.println("Mensajes: ");
            for (String msg: messages) {
                System.out.println(msg);
            }
        }
    }
}
