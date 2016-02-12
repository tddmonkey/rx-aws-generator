package generator.dynamo;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import rx.Observable;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class RxMethod {
    private Method amazonMethod;

    public RxMethod(Method amazonMethod) {
        this.amazonMethod = amazonMethod;
    }

    public MethodSpec generateMethodSpec(String amazonClientFieldName) {
        String targetMethodName = amazonMethod.getName();
        ParameterSpec parameter = parameter();
        ParameterizedType returnType = returnType();
        String handlerMethod = returnType.getActualTypeArguments()[0] == Void.class ? "voidHandlerFor" : "valueEmittingHandlerFor";
        MethodSpec main = MethodSpec.methodBuilder(sanitizedName())
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addParameter(parameter)
                .addStatement("return $T.create($N -> $N.$L($N, AmazonWebServiceRequestAsyncHandler.$L($N)))", Observable.class, "subscriber", amazonClientFieldName, targetMethodName, parameter.name, handlerMethod, "subscriber")
                .build();
        return main;
    }

    private ParameterSpec parameter() {
        Parameter inputParameter = amazonMethod.getParameters()[0];
        Class<?> type = inputParameter.getType();
        String name = inputParameter.getName().equals("arg0") ? lowercaseFirst(type.getSimpleName()) : inputParameter.getName();
        return ParameterSpec
                .builder(type, name)
                .build();
    }

    private ParameterizedType returnType() {
        ParameterizedType type = (ParameterizedType) amazonMethod.getParameters()[1].getParameterizedType();
        Type[] actualTypeArguments = type.getActualTypeArguments();
        return typeFor(actualTypeArguments[1]);

    }

    private ParameterizedType typeFor(Type argument) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { argument };
            }

            @Override
            public Type getRawType() {
                return Observable.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    private String sanitizedName() {
        return amazonMethod.getName().replace("Async", "");
    }

    static String lowercaseFirst(String s) {
        return s.substring(0, 1).toLowerCase() +
                s.substring(1);
    }
}
