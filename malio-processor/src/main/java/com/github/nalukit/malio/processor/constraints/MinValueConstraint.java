package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.MaxValue;
import com.github.nalukit.malio.shared.annotation.field.MinValue;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.util.Arrays;
import java.util.List;

public class MinValueConstraint extends AbstractConstraint<MinValue> {

    public MinValueConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils, AbstractGenerator generator) {
        super(processingEnv, processorUtils, generator);
    }

    @Override
    public Class<MinValue> annotationType() {
        return MinValue.class;
    }


    @Override
    protected String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_MINVALUE_IMPL_NAME;
    }

    @Override
    protected ConstraintType getConstraintType() {
        return ConstraintType.MIN_VALUE_CONSTRAINT;
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
