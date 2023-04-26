package com.github.nalukit.malio.processor.constraints;

import com.github.nalukit.malio.processor.constraints.generator.AbstractGenerator;
import com.github.nalukit.malio.processor.constraints.scanner.AbstractScanner;
import com.github.nalukit.malio.processor.util.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class AbstractConstraint<T extends Annotation> {
    public AbstractGenerator generator;
    public AbstractScanner scanner;
    protected ProcessingEnvironment processingEnvironment;
    protected ProcessorUtils processorUtils;

    public AbstractConstraint(ProcessingEnvironment processingEnv, ProcessorUtils processorUtils) {
        this.processingEnvironment = processingEnv;
        this.processorUtils = processorUtils;
    }

    public abstract Class<T> annotationType();

    public abstract List<Element> getDiesDas(TypeElement element);
}
