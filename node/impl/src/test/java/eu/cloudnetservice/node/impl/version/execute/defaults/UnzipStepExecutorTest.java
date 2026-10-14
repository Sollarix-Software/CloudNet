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

import eu.cloudnetservice.node.impl.version.information.VersionInstaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

public class UnzipStepExecutorTest {

  @Test
  void testPathTraversalRejected(@TempDir Path tempDir) throws IOException {
    Path zipFile = tempDir.resolve("test_traversal.zip");
    Path workDir = tempDir.resolve("work");
    Files.createDirectories(workDir);

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ZipOutputStream zos = new ZipOutputStream(baos)) {
      ZipEntry entry = new ZipEntry("../outside.txt");
      zos.putNextEntry(entry);
      zos.write("test content".getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
      zos.finish();

      Files.write(zipFile, baos.toByteArray());
    }

    UnzipStepExecutor executor = new UnzipStepExecutor();
    VersionInstaller mockInstaller = Mockito.mock(VersionInstaller.class);

    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(mockInstaller, workDir, Set.of(zipFile))
    );
  }
}
