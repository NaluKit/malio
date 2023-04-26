package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.NotNull;
import com.github.nalukit.malio.shared.annotation.field.Whitelist;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.util.Arrays;
import java.util.List;

public class NotNullConstraint extends AbstractConstraint<NotNull> {

    public NotNullConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils, AbstractGenerator generator) {
        super(processingEnv, processorUtils, generator);
    }

    @Override
    public Class<NotNull> annotationType() {
        return NotNull.class;
    }


    @Override
    protected String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_NOT_NULL_IMPL_NAME;
    }

    @Override
    protected ConstraintType getConstraintType() {
        return ConstraintType.NOT_NULL_CONSTRAINT;
    }

    @Override
    protected List<TypeKind> getSupportedPrimitives() {
        return null;
    }

    @Override
    protected List<Class> getSupportedDeclaredType() {
        return null;
    }

}
