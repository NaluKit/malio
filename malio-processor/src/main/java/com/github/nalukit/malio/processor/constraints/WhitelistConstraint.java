package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraints.generator.ConstraintWhitelistGenerator;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;
import com.github.nalukit.malio.shared.annotation.field.Whitelist;
import com.github.nalukit.malio.shared.internal.constraints.AbstractWhitelistConstraint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.util.Collections;
import java.util.List;

public class WhitelistConstraint extends AbstractConstraint<Whitelist> {

    public WhitelistConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils) {
        super(processingEnv, processorUtils);
    }

    @Override
    public Class<Whitelist> annotationType() {
        return Whitelist.class;
    }

    @Override
    public String getImplementationName() {
        return Constants.MALIO_CONSTRAINT_WHITELIST_IMPL_NAME;
    }

    @Override
    public ConstraintType getConstraintType() {
        return ConstraintType.WHITELIST_CONSTRAINT;
    }

    @Override
    public TypeName getValidationClass(VariableElement variableElement) {
        return ClassName.get(AbstractWhitelistConstraint.class);
    }

    @Override
    protected List<TypeKind> getSupportedPrimitives() {
        return null;
    }

    @Override
    protected List<Class<?>> getSupportedDeclaredType() {
        return Collections.singletonList(String.class);
    }

    @Override
    protected AbstractGenerator createGenerator() {
        return ConstraintWhitelistGenerator.builder()
                .elements(this.processingEnvironment.getElementUtils())
                .filer(this.processingEnvironment.getFiler())
                .types(this.processingEnvironment.getTypeUtils())
                .processorUtils(this.processorUtils)
                .constraint(this)
                .build();
    }


}
