package generator.common;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import rx.Observable;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Method;

public class RxAwsSdkGenerator {
    public TypeSpec generateSpec() {
        return generateRxClass(fetchMethodsToConvert().map(this::generateRxMethod));
    }

    private TypeSpec generateRxClass(Observable<RxMethod> methods) {
        TypeSpec.Builder rxSqs = TypeSpec.classBuilder("AmazonSdkRxDynamoDb")
            .addModifiers(Modifier.PUBLIC);

        rxSqs.addField(amazonClientField());
        rxSqs.addMethod(constructor());
        methods
            .map(rxMethod -> rxMethod.generateMethodSpec("amazonClient"))
            .subscribe(rxSqs::addMethod);

        return rxSqs.build();
    }

    /**
     * return Observable.create(subscriber -> client.listQueuesAsync(handlerFor(subscriber)));
     * @return
     */

    private FieldSpec amazonClientField() {
        return FieldSpec.builder(AmazonSQSAsync.class, "amazonClient")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();
    }

    private MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(AmazonSQSAsync.class, "amazonClient")
                .addStatement("this.$N = $N", "amazonClient", "amazonClient")
                .build();
    }


    private RxMethod generateRxMethod(Method m) {
        return new RxMethod(m);
    }

    private Observable<Method> fetchMethodsToConvert() {
        Method[] methods = AmazonSQSAsync.class.getMethods();
        return Observable.from(methods)
                .filter(m -> m.getName().endsWith("Async"))
                .filter(m -> m.getParameterTypes().length == 2 && m.getParameterTypes()[1].isAssignableFrom(AsyncHandler.class));

    }
}
