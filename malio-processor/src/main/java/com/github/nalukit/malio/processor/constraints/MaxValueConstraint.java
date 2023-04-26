package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.model.ConstraintModel;
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

    public MaxValueConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils, AbstractGenerator generator) {
        super(processingEnv, processorUtils, generator);
    }

    @Override
    public Class<MaxValue> annotationType() {
        return MaxValue.class;
    }


    @Override
    protected String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_MAXVALUE_IMPL_NAME;
    }

    @Override
    protected ConstraintType getConstraintType() {
        return ConstraintType.MAX_VALUE_CONSTRAINT;
    }

    @Override
    protected List<TypeKind> getSupportedPrimitives() {
        return Arrays.asList(TypeKind.INT, TypeKind.LONG);
    }

    @Override
    protected List<Class> getSupportedDeclaredType() {
        return Arrays.asList(Integer.class, Long.class);
    }


}
