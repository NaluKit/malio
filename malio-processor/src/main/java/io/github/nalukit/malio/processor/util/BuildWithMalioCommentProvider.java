/*
 * Copyright © 2023 Frank Hossfeld, Philipp Kohl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nalukit.malio.processor.util;

import com.squareup.javapoet.CodeBlock;
import io.github.nalukit.malio.processor.ProcessorConstants;

public class BuildWithMalioCommentProvider {

  public static BuildWithMalioCommentProvider INSTANCE = new BuildWithMalioCommentProvider();

  private BuildWithMalioCommentProvider() {
  }

  public CodeBlock getGeneratedComment() {
    String sb = "Build with Malio version '" + ProcessorConstants.PROCESSOR_VERSION + "' at " + ProcessorConstants.BUILD_TIME;
    return CodeBlock.builder()
                    .add(sb)
                    .build();
  }

}
