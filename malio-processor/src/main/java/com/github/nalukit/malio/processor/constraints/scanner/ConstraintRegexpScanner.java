package com.github.nalukit.malio.processor.constraints.scanner;

import com.github.nalukit.malio.processor.Constants;
import com.github.nalukit.malio.processor.model.ConstraintModel;
import com.github.nalukit.malio.processor.model.ConstraintType;
import com.github.nalukit.malio.processor.util.ProcessorUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ConstraintRegexpScanner
    extends AbstractScanner {

  private final TypeElement     validatorElement;
  private final VariableElement variableElement;

  private ConstraintRegexpScanner(Builder builder) {
    this.validatorElement = builder.validatorElement;
    this.variableElement  = builder.variableElement;
    this.elements         = builder.elements;
    this.types            = builder.types;
    this.processorUtils   = builder.processorUtils;
  }

  public static Builder builder() {
    return new Builder();
  }

  public ConstraintModel createConstraintModel() {
    return new ConstraintModel(this.processorUtils.getPackageAsString(this.validatorElement),
                               validatorElement.getSimpleName()
                                               .toString(),
                               this.variableElement.toString(),
                               Constants.MALIO_CONSTRAINT_REGEXP_IMPL_NAME,
                               ConstraintType.REGEXP_CONSTRAINT);
  }

  public static class Builder {

    TypeElement     validatorElement;
    VariableElement variableElement;
    Elements        elements;
    Types           types;
    ProcessorUtils  processorUtils;

    public Builder elements(Elements elements) {
      this.elements = elements;
      return this;
    }

    public Builder validatorElement(Element validatorElement) {
      this.validatorElement = (TypeElement) validatorElement;
      return this;
    }

    public Builder variableElement(Element variableElement) {
      this.variableElement = (VariableElement) variableElement;
      return this;
    }

    public Builder types(Types types) {
      this.types = types;
      return this;
    }

    public Builder processorUtils(ProcessorUtils processorUtils) {
      this.processorUtils = processorUtils;
      return this;
    }

    public ConstraintRegexpScanner build() {
      return new ConstraintRegexpScanner(this);
    }
  }
}
