package com.github.nalukit.malio.processor.constraints.scanner;

import com.github.nalukit.malio.processor.util.ProcessorUtils;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class AbstractScanner {

  protected Elements       elements;
  protected Types          types;
  protected ProcessorUtils processorUtils;

}
