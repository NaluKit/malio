
package com.github.nalukit.malio.processor.util;

import com.github.nalukit.malio.processor.ProcessorConstants;
import com.squareup.javapoet.CodeBlock;

public class BuildWithMalioCommentProvider {

  public static BuildWithMalioCommentProvider INSTANCE = new BuildWithMalioCommentProvider();

  private BuildWithMalioCommentProvider() {
  }

  public CodeBlock getGeneratedComment() {
    String sb = "Build with Malio version >>" +
                ProcessorConstants.PROCESSOR_VERSION +
                "<< at " +
                ProcessorConstants.BUILD_TIME;
    return CodeBlock.builder()
                    .add(sb)
                    .build();
  }

}
