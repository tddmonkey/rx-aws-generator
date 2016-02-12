package generator.common;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

public class RxAwsSdkClientGenerator {
    private final Class<?> amazonClient;
    private final String targetPackage;
    private final String targetClass;

    public RxAwsSdkClientGenerator(Class<?> amazonClient, String targetPackage, String targetClass) {
        this.amazonClient = amazonClient;
        this.targetPackage = targetPackage;
        this.targetClass = targetClass;
    }

    public void generateTo(File outputFile) throws IOException {
        TypeSpec rxSqs = new RxAwsSdkGenerator(amazonClient, targetClass).generateSpec();
        JavaFile javaFile = JavaFile.builder(targetPackage, rxSqs).build();
        javaFile.writeTo(outputFile);
    }
}
