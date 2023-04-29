package com.github.nalukit.malio.processor.constraints.generator;

import com.github.nalukit.malio.processor.ProcessorException;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

public interface IsGenerator {
    void generate(Element validatorElement, VariableElement variableElement) throws ProcessorException;
}
