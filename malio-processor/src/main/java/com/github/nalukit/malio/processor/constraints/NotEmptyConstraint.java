package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraints.generator.ConstraintNotEmptyGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.NotEmpty;
import com.github.nalukit.malio.shared.internal.constraints.AbstractNotEmptyConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.Collection;
import java.util.List;

public class NotEmptyConstraint extends AbstractConstraint<NotEmpty> {

    public NotEmptyConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils) {
        super(processingEnv, processorUtils);
    }

    @Override
    public Class<NotEmpty> annotationType() {
        return NotEmpty.class;
    }

    @Override
    public String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_NOTEMPTY_IMPL_NAME;
    }

    @Override
    public ConstraintType getConstraintType() {
        return ConstraintType.NOT_EMPTY_CONSTRAINT;
    }

    @Override
    public TypeName getValidationClass(VariableElement variableElement) {
        return ClassName.get(AbstractNotEmptyConstraint.class);
    }

    @Override
    protected List<TypeKind> getSupportedPrimitives() {
        return null;
    }

    @Override
    protected List<Class> getSupportedDeclaredType() {
        return List.of(Collection.class);
    }

    @Override
    protected AbstractGenerator createGenerator() {
        return ConstraintNotEmptyGenerator.builder()
                .elements(this.processingEnvironment.getElementUtils())
                .filer(this.processingEnvironment.getFiler())
                .types(this.processingEnvironment.getTypeUtils())
                .processorUtils(this.processorUtils)
                .constraint(this)
                .build();
    }


}
