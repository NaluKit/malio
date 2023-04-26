package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.MaxValue;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.util.Arrays;
import java.util.List;

public class MaxValueConstraint extends AbstractConstraint<MaxValue> {
    private final List<TypeKind> supportedPrimitives = Arrays.asList(TypeKind.INT, TypeKind.LONG);
    @SuppressWarnings("rawtypes")
    private final List<Class> supportedDeclaredType = Arrays.asList(Integer.class, Long.class);
    private final String IMPLEMENTATION_NAME = Constants.MALIO_CONSTRAINT_MAXVALUE_IMPL_NAME;
    private final ConstraintType constraintType = ConstraintType.MAX_VALUE_CONSTRAINT;
    public MaxValueConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils) {
        super(processingEnv, processorUtils);
    }

    @Override
    public Class<MaxValue> annotationType() {
        return MaxValue.class;
    }

    @Override
    public List<Element> getDiesDas(TypeElement element) {
        return this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnvironment,
                element, this.annotationType());
    }
}
