package generator.common;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsync;

import java.io.File;
import java.io.IOException;

public class Start {
    public static enum CLIENT_TYPE {
        RXSQS(AmazonSQSAsync.class, "tddmonkey.rxsqs.awssdk", "AmazonSdkRxSqs"),
        RXDYNAMO(AmazonDynamoDBAsync.class, "tddmonkey.rxdynamo.awssdk", "AmazonSdkRxDynamoDb"),
        RXSNS(AmazonSNSAsync.class, "tddmonkey.rxsns.awssdk", "AmazonSdkRxSns");

        private final Class<?> amazonClientToProxy;
        private final String outputPackage;
        private final String clientClassName;

        CLIENT_TYPE(Class<?> amazonClientToProxy, String outputPackage, String clientClassName) {
            this.amazonClientToProxy = amazonClientToProxy;
            this.outputPackage = outputPackage;
            this.clientClassName = clientClassName;
        }
    }
    public static void main(String[] args) throws IOException {
        CLIENT_TYPE clientType = CLIENT_TYPE.valueOf(args[0]);
        new RxAwsSdkClientGenerator(clientType.amazonClientToProxy, clientType.outputPackage, clientType.clientClassName).generateTo(new File(args[1]));
    }
}
