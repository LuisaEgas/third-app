package aws.example.s3;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesSQS {

    public AmazonSQS sqs2;

    public List<String> ExampleSQS() {
        List<String> body = new ArrayList<>();
        String queueUrl = "https://sqs.us-west-1.amazonaws.com/251799574311/prueba.fifo";
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion("us-west-1").build();
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
        while (!messages.isEmpty()) {
            for (Message m : messages) {
                var deleteMessageRequest = new DeleteMessageRequest();
                deleteMessageRequest.setQueueUrl(queueUrl);
                deleteMessageRequest.setReceiptHandle(m.getReceiptHandle());
                body.add(m.getBody());
                var response = sqs.deleteMessage(deleteMessageRequest);
            }
            messages = sqs.receiveMessage(queueUrl).getMessages();
        }
        return body;
    }
}
