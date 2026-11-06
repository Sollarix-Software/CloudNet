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

  private Path testDir;
  private Path workingDirectory;

  @BeforeEach
  void setUp() {
    this.testDir = FileUtil.createTempFile();
    this.workingDirectory = this.testDir.resolve("workingDir");
    FileUtil.createDirectory(this.workingDirectory);
  }

  @AfterEach
  void tearDown() {
    FileUtil.delete(this.testDir);
  }

  @Test
  void testUnzipStepExecutorSuccess() throws IOException {
    var zipFile = this.testDir.resolve("valid.zip");
    try (var zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      var entry = new ZipEntry("test.txt");
      zos.putNextEntry(entry);
      zos.write("test content".getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    var resultPaths = executor.execute(installer, this.workingDirectory, Set.of(zipFile));

    var expectedPath = this.workingDirectory.resolve("test.txt");
    Assertions.assertTrue(resultPaths.contains(expectedPath));
    Assertions.assertTrue(Files.exists(expectedPath));
    Assertions.assertEquals("test content", Files.readString(expectedPath));
  }

  @Test
  void testUnzipStepExecutorBlocksPathTraversal() throws IOException {
    var zipFile = this.testDir.resolve("traversal.zip");
    try (var zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      var entry = new ZipEntry("../evil.txt");
      zos.putNextEntry(entry);
      zos.write("evil".getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(installer, this.workingDirectory, Set.of(zipFile)));
  }

  @Test
  void testUnzipStepExecutorBlocksEmptyNameRootTraversal() throws IOException {
    var zipFile = this.testDir.resolve("empty.zip");
    try (var zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      var entry = new ZipEntry("");
      zos.putNextEntry(entry);
      zos.write("evil".getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(installer, this.workingDirectory, Set.of(zipFile)));
  }
}
