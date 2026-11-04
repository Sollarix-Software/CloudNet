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

  @BeforeEach
  void setUp() throws IOException {
    this.testDir = Files.createTempDirectory("unzip-test");
  }

  @AfterEach
  void tearDown() {
    FileUtil.delete(this.testDir);
  }

  @Test
  void testNormalUnzip() throws Exception {
    var zipFile = this.testDir.resolve("test.zip");
    var workingDir = this.testDir.resolve("workingDir");
    Files.createDirectories(workingDir);

    try (var zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      zos.putNextEntry(new ZipEntry("sub/nested.txt"));
      zos.write("hello world".getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    var result = executor.execute(installer, workingDir, Set.of(zipFile));

    var extractedFile = workingDir.resolve("sub/nested.txt");
    Assertions.assertTrue(Files.exists(extractedFile));
    Assertions.assertEquals("hello world", Files.readString(extractedFile));
    Assertions.assertTrue(result.contains(extractedFile));
  }

  @Test
  void testZipSlipTraversalFails() throws Exception {
    var zipFile = this.testDir.resolve("malicious.zip");
    var workingDir = this.testDir.resolve("workingDir");
    Files.createDirectories(workingDir);

    try (var zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      zos.putNextEntry(new ZipEntry("../traversal.txt"));
      zos.write("evil".getBytes(StandardCharsets.UTF_8));
      zos.closeEntry();
    }

    var installer = Mockito.mock(VersionInstaller.class);
    var executor = new UnzipStepExecutor();
    Assertions.assertThrows(
      IllegalStateException.class,
      () -> executor.execute(installer, workingDir, Set.of(zipFile)));

    Assertions.assertFalse(Files.exists(this.testDir.resolve("traversal.txt")));
  }
}
