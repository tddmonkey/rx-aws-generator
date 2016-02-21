package generator.common;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import rx.Observable;
import tddmonkey.rxaws.common.AmazonWebServiceRequestAsyncHandler;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RxAskSdkMethod {
    private final ParameterizedType returnType;
    private Method amazonMethod;

    public RxAskSdkMethod(Method amazonMethod) {
        this.amazonMethod = amazonMethod;
        this.returnType = returnType();
    }

    public MethodSpec generateMethodSpec(String amazonClientFieldName) {
        MethodSpec.Builder mainBuilder = MethodSpec.methodBuilder(sanitizedName())
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType);
        Parameters parameters = parameters();
        addParameters(mainBuilder, parameters);
        addMethodBody(mainBuilder, parameters, amazonClientFieldName);

        return mainBuilder.build();
    }

    private Parameters parameters() {
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < amazonMethod.getParameters().length-1; i++) {
            Parameter parameter = amazonMethod.getParameters()[i];
            if (!parameter.isNamePresent()) {
                throw new IllegalStateException("Parameter names are not present- ensure you are using a version of the AWS classes compiled with the -parameters option");
            }
            parameters.add(parameter);
        }
        return new Parameters(parameters);
    }

    private void addMethodBody(MethodSpec.Builder mainBuilder, Parameters parameters, String amazonClientFieldName) {
        String handlerMethod = returnType.getActualTypeArguments()[0] == Void.class ? "voidHandlerFor" : "valueEmittingHandlerFor";
        String targetMethodName = amazonMethod.getName();

        List<String> parameterNames = new ArrayList<>();
        String parameterPlaceholders = String.join(", ", Collections.nCopies(parameters.count(), "$N"));

        List<Object> statementArguments = new ArrayList<>();
        statementArguments.add(Observable.class);
        statementArguments.add("subscriber");
        statementArguments.add(amazonClientFieldName);
        statementArguments.add(targetMethodName);
        statementArguments.addAll(parameters.parameters.stream().map(p -> p.getName()).collect(Collectors.toList()));
        statementArguments.add(AmazonWebServiceRequestAsyncHandler.class);
        statementArguments.add(handlerMethod);
        statementArguments.add("subscriber");
//        mainBuilder.addStatement("return $T.create($N -> $N.$L($N, $T.$L($N)))", Observable.class, "subscriber", amazonClientFieldName, targetMethodName, parameter.name, AmazonWebServiceRequestAsyncHandler.class, handlerMethod, "subscriber");
        mainBuilder.addStatement(String.format("return $T.create($N -> $N.$L(%s, $T.$L($N)))", parameterPlaceholders), statementArguments.toArray());
    }

    private void addParameters(MethodSpec.Builder mainBuilder, Parameters parameters) {
        parameters.parameters.stream().forEach(parameter ->
            mainBuilder.addParameter(ParameterSpec
                    .builder(parameter.getType(), parameter.getName())
                    .build())
        );
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
        ParameterizedType type = (ParameterizedType) amazonMethod.getParameters()[amazonMethod.getParameters().length-1].getParameterizedType();
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

    private class Parameters {
        private List<Parameter> parameters;

        public Parameters(List<Parameter> parameters) {
            this.parameters = parameters;
        }

        public int count() {
            return parameters.size();
        }
    }
}
