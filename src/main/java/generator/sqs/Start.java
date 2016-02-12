package generator.sqs;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import generator.common.RxAwsSdkClientGenerator;

import java.io.File;
import java.io.IOException;

public class Start {
    public static void main(String[] args) throws IOException {
        new RxAwsSdkClientGenerator(AmazonSQSAsync.class, "tddmonkey.rxsqs.awssdk", "AmazonSdkRxSqs").generateTo(new File(args[0]));
    }
}
