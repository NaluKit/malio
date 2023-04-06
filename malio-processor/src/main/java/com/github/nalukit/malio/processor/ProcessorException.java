package com.github.nalukit.malio.processor;

public class ProcessorException
    extends Exception {

  public ProcessorException() {
    super();
  }

  public ProcessorException(String message) {
    super(message);
  }

  public ProcessorException(String message,
                            Throwable cause) {
    super(message,
          cause);
  }

  public ProcessorException(Throwable cause) {
    super(cause);
  }

  public ProcessorException(String message,
                            Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
    super(message,
          cause,
          enableSuppression,
          writableStackTrace);
  }

}
