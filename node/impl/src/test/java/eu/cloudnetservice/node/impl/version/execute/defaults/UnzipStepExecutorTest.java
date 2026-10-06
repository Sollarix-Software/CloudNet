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
  void testUnzipStepExecutorSuccess(@TempDir Path tempDir) throws IOException {
    var workingDir = tempDir.resolve("work");
    Files.createDirectories(workingDir);

    var zipPath = tempDir.resolve("test.zip");
    try (var zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      zipOut.putNextEntry(new ZipEntry("test.txt"));
      zipOut.write("Hello World".getBytes(StandardCharsets.UTF_8));
      zipOut.closeEntry();
    }

    var executor = new UnzipStepExecutor();
    var mockInstaller = Mockito.mock(VersionInstaller.class);
    var extracted = executor.execute(mockInstaller, workingDir, Set.of(zipPath));

    Assertions.assertEquals(1, extracted.size());
    var extractedFile = workingDir.resolve("test.txt");
    Assertions.assertTrue(Files.exists(extractedFile));
    Assertions.assertEquals("Hello World", Files.readString(extractedFile));
  }

  @Test
  void testUnzipStepExecutorPathTraversalThrows(@TempDir Path tempDir) throws IOException {
    var workingDir = tempDir.resolve("work");
    Files.createDirectories(workingDir);

    var zipPath = tempDir.resolve("malicious.zip");
    try (var zipOut = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      zipOut.putNextEntry(new ZipEntry("../traversal.txt"));
      zipOut.write("Malicious Data".getBytes(StandardCharsets.UTF_8));
      zipOut.closeEntry();
    }

    var executor = new UnzipStepExecutor();
    var mockInstaller = Mockito.mock(VersionInstaller.class);

    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(mockInstaller, workingDir, Set.of(zipPath))
    );
  }
}
