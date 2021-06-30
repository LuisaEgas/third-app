package aws.test.s3;

import aws.example.s3.Constants;
import aws.example.s3.MessagesSQS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ExampleTest {

    MessagesSQS messagesSQS = new MessagesSQS();

    @Test
    public void testSQS() {
        List<String> list = new ArrayList<>();
        Assertions.assertEquals(list.isEmpty(), messagesSQS.ExampleSQS().isEmpty());
    }

    @Test
    public void testGetParameter() {
        final String nameSqs = MessagesSQS.GetParameter(Constants.SQS_PARAMETER);
        Assertions.assertEquals("prueba.fifo", nameSqs);
    }

    @Test
    public void testGetQueueUrl() {
        final String urlSqs = messagesSQS.GetQueueUrl(Constants.SQS_PARAMETER);
        Assertions.assertEquals(urlSqs,"https://sqs.us-west-1.amazonaws.com/251799574311/prueba.fifo");
    }

    @Test
    public void testExampleSQS() {
        messagesSQS.ExampleSQS();
    }
}
