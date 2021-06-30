package aws.example.s3;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class MessagesSQS {

    private final SqsClient sqsClient;

    public MessagesSQS() {
        sqsClient = SqsClient.builder().region(Region.US_WEST_1).build();
    }

    public String GetQueueUrl(String parameterSqs) {
        try {
            final GetQueueUrlRequest queueUrlRequest = GetQueueUrlRequest.builder()
                    .queueName(GetParameter(parameterSqs)).build();
            final GetQueueUrlResponse result = sqsClient.getQueueUrl(queueUrlRequest);
            log.info(result.queueUrl());
            return result.queueUrl();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public static String GetParameter(String parameterSqs) {
        try {
            final GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(parameterSqs).build();
            final SsmClient ssmClient = SsmClient.builder().region(Region.US_WEST_1).build();
            final GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            log.info(parameterResponse.parameter().value());
            return parameterResponse.parameter().value();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<String> ExampleSQS() {
        List<String> body = new ArrayList<>();
        final String queueUrl = GetQueueUrl(Constants.SQS_PARAMETER);
        final ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).build();
        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();
        while (!messages.isEmpty()) {
            messages.forEach(message -> {
                String messageSQS = null;
                try {
                    messageSQS = DeserializeBase64(message.body()).toString();
                } catch (IOException e) {
                    log.error(e.toString());
                }
                body.add(messageSQS);
            final DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build();
            sqsClient.deleteMessage(deleteMessageRequest);
                log.info(messageSQS);
            });
            messages = sqsClient.receiveMessage(receiveRequest).messages();
        }
        return body;
    }


    public static Object DeserializeBase64(String data) throws IOException {
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject().toString();
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new InternalError(e.getMessage());
        }
    }
}
