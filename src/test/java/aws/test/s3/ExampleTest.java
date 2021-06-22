package aws.test.s3;

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
}
