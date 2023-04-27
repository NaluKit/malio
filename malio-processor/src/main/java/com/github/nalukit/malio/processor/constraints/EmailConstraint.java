package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraints.generator.ConstraintEmailGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.Email;
import com.github.nalukit.malio.shared.internal.constraints.AbstractEmailConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.List;

public class EmailConstraint extends AbstractConstraint<Email> {

    public EmailConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils) {
        super(processingEnv, processorUtils);
    }

    @Override
    public Class<Email> annotationType() {
        return Email.class;
    }

    @Override
    public String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_EMAIL_IMPL_NAME;
    }

    @Override
    public ConstraintType getConstraintType() {
        return ConstraintType.EMAIL_CONSTRAINT;
    }

    @Override
    public TypeName getValidationClass(VariableElement variableElement) {
        return ClassName.get(AbstractEmailConstraint.class);
    }

    @Override
    protected List<TypeKind> getSupportedPrimitives() {
        return null;
    }

    @Override
    protected List<Class> getSupportedDeclaredType() {
        return List.of(String.class);
    }

    @Override
    protected AbstractGenerator createGenerator() {
        return ConstraintEmailGenerator.builder()
                .elements(this.processingEnvironment.getElementUtils())
                .filer(this.processingEnvironment.getFiler())
                .types(this.processingEnvironment.getTypeUtils())
                .processorUtils(this.processorUtils)
                .constraint(this)
                .build();
    }


}
