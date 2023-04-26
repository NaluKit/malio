package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.MinLength;
import com.github.nalukit.malio.shared.annotation.field.MinValue;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.util.Arrays;
import java.util.List;

public class MinLengthConstraint extends AbstractConstraint<MinLength> {

    public MinLengthConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils, AbstractGenerator generator) {
        super(processingEnv, processorUtils, generator);
    }

    @Override
    public Class<MinLength> annotationType() {
        return MinLength.class;
    }

    @Override
    protected String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_MINLENGTH_IMPL_NAME;
    }

    @Override
    protected ConstraintType getConstraintType() {
        return ConstraintType.MIN_LENGTH_CONSTRAINT;
    }

    @Override
    protected List<TypeKind> getSupportedPrimitives() {
        return null;
    }

    @Override
    protected List<Class> getSupportedDeclaredType() {
        return List.of(String.class);
    }


}
