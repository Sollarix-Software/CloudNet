/*
 * Copyright 2019-present CloudNetService team & contributors
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

package eu.cloudnetservice.node.impl.version.execute.defaults;

import eu.cloudnetservice.node.impl.version.execute.InstallStepExecutor;
import eu.cloudnetservice.node.impl.version.information.VersionInstaller;
import eu.cloudnetservice.utils.base.io.FileUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.NonNull;

public class UnzipStepExecutor implements InstallStepExecutor {

  @Override
  public @NonNull Set<Path> execute(
    @NonNull VersionInstaller installer,
    @NonNull Path workingDirectory,
    @NonNull Set<Path> inputPaths
  ) throws IOException {
    Set<Path> resultPaths = new HashSet<>();

    for (var path : inputPaths) {
      try (var zipInputStream = new ZipInputStream(Files.newInputStream(path))) {
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
          var targetPath = workingDirectory.resolve(entry.getName());

          // Ensure the target path is inside the target working directory to prevent Zip Slip / path traversal attacks
          FileUtil.ensureChild(workingDirectory, targetPath);

          resultPaths.add(targetPath);

          if (entry.isDirectory()) {
            Files.createDirectory(targetPath);
          } else {
            Files.copy(zipInputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
          }
        }
      }
    }

    return resultPaths;
  }
}
