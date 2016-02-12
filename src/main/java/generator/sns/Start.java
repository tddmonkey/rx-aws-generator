package generator.sns;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import generator.common.RxAwsSdkClientGenerator;
import generator.common.RxAwsSdkGenerator;

import java.io.File;
import java.io.IOException;

public class Start {
    public static void main(String[] args) throws IOException {
        new RxAwsSdkClientGenerator(AmazonSNSAsync.class, "tddmonkey.rxsns.awssdk", "AmazonSdkRxSns").generateTo(new File(args[0]));
    }
}
