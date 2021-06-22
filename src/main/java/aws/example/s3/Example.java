package aws.example.s3;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

public class Example {

    public static void main(String[] args) {

        MessagesSQS messagesSQS = new MessagesSQS();
        List<String> messages = messagesSQS.ExampleSQS();
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
