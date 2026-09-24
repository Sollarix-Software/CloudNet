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
  public void testUnzipNormalFile(@TempDir Path tempDir) throws IOException {
    var zipFile = tempDir.resolve("test.zip");
    var workDir = tempDir.resolve("work");
    Files.createDirectories(workDir);

    try (var out = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      out.putNextEntry(new ZipEntry("sub/test.txt"));
      out.write("hello".getBytes(StandardCharsets.UTF_8));
      out.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    var results = executor.execute(installer, workDir, Set.of(zipFile));

    Assertions.assertEquals(1, results.size());
    var extractedFile = workDir.resolve("sub/test.txt");
    Assertions.assertTrue(Files.exists(extractedFile));
    Assertions.assertEquals("hello", Files.readString(extractedFile));
  }

  @Test
  public void testUnzipPathTraversalBlocked(@TempDir Path tempDir) throws IOException {
    var zipFile = tempDir.resolve("malicious.zip");
    var workDir = tempDir.resolve("work");
    Files.createDirectories(workDir);

    try (var out = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      out.putNextEntry(new ZipEntry("../evil.txt"));
      out.write("evil".getBytes(StandardCharsets.UTF_8));
      out.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(installer, workDir, Set.of(zipFile))
    );

    Assertions.assertFalse(Files.exists(tempDir.resolve("evil.txt")));
  }
}
