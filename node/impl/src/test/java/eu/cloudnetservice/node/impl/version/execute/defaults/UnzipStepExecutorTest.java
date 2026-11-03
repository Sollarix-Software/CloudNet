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
import eu.cloudnetservice.utils.base.io.FileUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UnzipStepExecutorTest {

  private Path tempDir;
  private Path workingDirectory;

  @BeforeEach
  void setUp() throws IOException {
    this.tempDir = Files.createTempDirectory("unzip-step-test");
    this.workingDirectory = this.tempDir.resolve("working");
    Files.createDirectories(this.workingDirectory);
  }

  @AfterEach
  void tearDown() {
    FileUtil.delete(this.tempDir);
  }

  @Test
  void testSuccessfulUnzip() throws IOException {
    var zipPath = this.tempDir.resolve("test.zip");
    try (var out = new ZipOutputStream(Files.newOutputStream(zipPath))) {
      out.putNextEntry(new ZipEntry("dir/"));
      out.closeEntry();
      out.putNextEntry(new ZipEntry("dir/sample.txt"));
      out.write("Hello World".getBytes(StandardCharsets.UTF_8));
      out.closeEntry();
      out.putNextEntry(new ZipEntry("root.txt"));
      out.write("Root content".getBytes(StandardCharsets.UTF_8));
      out.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();

    var result = executor.execute(installer, this.workingDirectory, Set.of(zipPath));

    Assertions.assertEquals(3, result.size());
    Assertions.assertTrue(Files.exists(this.workingDirectory.resolve("dir")));
    Assertions.assertTrue(Files.isDirectory(this.workingDirectory.resolve("dir")));
    Assertions.assertTrue(Files.exists(this.workingDirectory.resolve("dir/sample.txt")));
    Assertions.assertEquals(
      "Hello World",
      Files.readString(this.workingDirectory.resolve("dir/sample.txt"), StandardCharsets.UTF_8));
    Assertions.assertTrue(Files.exists(this.workingDirectory.resolve("root.txt")));
    Assertions.assertEquals(
      "Root content",
      Files.readString(this.workingDirectory.resolve("root.txt"), StandardCharsets.UTF_8));
  }

  @Test
  void testZipSlipTraversalThrowsException() throws IOException {
    var maliciousZipPath = this.tempDir.resolve("malicious.zip");
    try (var out = new ZipOutputStream(Files.newOutputStream(maliciousZipPath))) {
      out.putNextEntry(new ZipEntry("../evil.txt"));
      out.write("evil".getBytes(StandardCharsets.UTF_8));
      out.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();

    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(installer, this.workingDirectory, Set.of(maliciousZipPath)));
    Assertions.assertFalse(Files.exists(this.tempDir.resolve("evil.txt")));
  }
}
