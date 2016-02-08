package generator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

public class Start {
    public static void main(String[] args) {
        TypeSpec rxSqs = new RxSqsGenerator().generateSpec();
        JavaFile javaFile = JavaFile.builder("tddmonkey.rxsqs.awssdk", rxSqs).build();

        try {
            javaFile.writeTo(new File(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
