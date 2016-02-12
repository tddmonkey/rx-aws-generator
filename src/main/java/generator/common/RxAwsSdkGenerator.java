package generator.common;

import com.amazonaws.handlers.AsyncHandler;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import rx.Observable;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Method;

public class RxAwsSdkGenerator {
    private Class<?> amazonClass;
    private String targetClassName;

    public RxAwsSdkGenerator(Class<?> amazonClass, String targetClassName) {
        this.amazonClass = amazonClass;
        this.targetClassName = targetClassName;
    }

    public TypeSpec generateSpec() {
        return generateRxClass(fetchMethodsToConvert().map(this::generateRxMethod));
    }

    private TypeSpec generateRxClass(Observable<RxAskSdkMethod> methods) {
        TypeSpec.Builder rxSqs = TypeSpec.classBuilder(targetClassName)
            .addModifiers(Modifier.PUBLIC);

        rxSqs.addField(amazonClientField());
        rxSqs.addMethod(constructor());
        methods
            .map(rxMethod -> rxMethod.generateMethodSpec("amazonClient"))
            .subscribe(rxSqs::addMethod);

        return rxSqs.build();
    }

    private FieldSpec amazonClientField() {
        return FieldSpec.builder(amazonClass, "amazonClient")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();
    }

    private MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(amazonClass, "amazonClient")
                .addStatement("this.$N = $N", "amazonClient", "amazonClient")
                .build();
    }


    private RxAskSdkMethod generateRxMethod(Method m) {
        return new RxAskSdkMethod(m);
    }

    private Observable<Method> fetchMethodsToConvert() {
        Method[] methods = amazonClass.getMethods();
        return Observable.from(methods)
                .filter(m -> m.getName().endsWith("Async"))
                .filter(m -> m.getParameterTypes().length == 2 && m.getParameterTypes()[1].isAssignableFrom(AsyncHandler.class));

    }
}
