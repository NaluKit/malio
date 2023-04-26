package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.exceptions.UnsupportedTypeException;
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class AbstractConstraint<T extends Annotation> {
    private AbstractGenerator generator;

    protected ProcessingEnvironment processingEnvironment;
    protected ProcessorUtils processorUtils;

    public AbstractConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils, AbstractGenerator generator) {
        this.processingEnvironment = processingEnv;
        this.processorUtils = processorUtils;
        this.generator = generator;
    }

    public ConstraintModel createConstraintModel(Element varType, Element varName) {
        return new ConstraintModel(this.processorUtils.getPackageAsString(varName),
                varType.getSimpleName().toString(),
                varName.getSimpleName().toString(),
                getImplementationName(),
                getConstraintType());
    }

    public void check(VariableElement variableElement) {
        if (getSupportedDeclaredType() == null && getSupportedPrimitives() == null) {
            return;
        }

        if (!processorUtils.checkDataType(variableElement,
                getSupportedPrimitives(),
                getSupportedDeclaredType())) {
            throw new UnsupportedTypeException("Type '" + variableElement.asType()  + "' not supported for " + getClass().getSimpleName());
        }
    }


    public abstract Class<T> annotationType();

    public List<Element> getVarsWithAnnotation(TypeElement element) {
        return this.processorUtils.getVariablesFromTypeElementAnnotatedWith(this.processingEnvironment,
                element, this.annotationType());
    }

    protected abstract String getImplementationName();
    protected abstract ConstraintType getConstraintType();

    protected abstract List<TypeKind> getSupportedPrimitives();
    protected abstract List<Class> getSupportedDeclaredType();

    public AbstractGenerator getGenerator() {
        return generator;
    }
}
