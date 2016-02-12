package generator.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import generator.common.RxAwsSdkClientGenerator;

import java.io.File;
import java.io.IOException;

public class Start {
    public static void main(String[] args) throws IOException {
        new RxAwsSdkClientGenerator(AmazonDynamoDBAsync.class, "tddmonkey.rxdynamo.awssdk", "AmazonSdkRxDynamoDb").generateTo(new File(args[0]));
    }
}
