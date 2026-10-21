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

package eu.cloudnetservice.node.impl.service.defaults.log;

import eu.cloudnetservice.driver.service.ServiceId;
import eu.cloudnetservice.node.config.Configuration;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ServiceLogCacheTest {

  private static class TestLogCache extends AbstractServiceLogCache {
    public TestLogCache(Configuration configuration, ServiceId serviceId) {
      super(configuration, serviceId);
    }

    public void addLogLine(String line) {
      this.handleItem(line, false);
    }
  }

  @Test
  void testLogCacheSizeLimit() {
    var config = Mockito.mock(Configuration.class);
    Mockito.when(config.maxServiceConsoleLogCacheSize()).thenReturn(3);

    var serviceId = ServiceId.builder()
      .taskName("TestTask")
      .environment("minecraft")
      .uniqueId(UUID.randomUUID())
      .taskServiceId(1)
      .build();

    var cache = new TestLogCache(config, serviceId);

    cache.addLogLine("Line 1");
    cache.addLogLine("Line 2");
    cache.addLogLine("Line 3");

    Assertions.assertEquals(3, cache.cachedLogMessages().size());
    Assertions.assertTrue(cache.cachedLogMessages().contains("Line 1"));

    cache.addLogLine("Line 4");

    Assertions.assertEquals(3, cache.cachedLogMessages().size());
    Assertions.assertFalse(cache.cachedLogMessages().contains("Line 1"));
    Assertions.assertTrue(cache.cachedLogMessages().contains("Line 2"));
    Assertions.assertTrue(cache.cachedLogMessages().contains("Line 3"));
    Assertions.assertTrue(cache.cachedLogMessages().contains("Line 4"));
  }

  @Test
  void testLogCacheSizeDynamicReduction() {
    var config = Mockito.mock(Configuration.class);
    Mockito.when(config.maxServiceConsoleLogCacheSize()).thenReturn(10);

    var serviceId = ServiceId.builder()
      .taskName("TestTask")
      .environment("minecraft")
      .uniqueId(UUID.randomUUID())
      .taskServiceId(1)
      .build();

    var cache = new TestLogCache(config, serviceId);

    for (int i = 1; i <= 10; i++) {
      cache.addLogLine("Line " + i);
    }
    Assertions.assertEquals(10, cache.cachedLogMessages().size());

    cache.logCacheSize(5);
    cache.addLogLine("Line 11");

    Assertions.assertEquals(5, cache.cachedLogMessages().size());
    Assertions.assertTrue(cache.cachedLogMessages().contains("Line 11"));
  }

  @Test
  void testLogCacheSizeZeroClearsCache() {
    var config = Mockito.mock(Configuration.class);
    Mockito.when(config.maxServiceConsoleLogCacheSize()).thenReturn(5);

    var serviceId = ServiceId.builder()
      .taskName("TestTask")
      .environment("minecraft")
      .uniqueId(UUID.randomUUID())
      .taskServiceId(1)
      .build();

    var cache = new TestLogCache(config, serviceId);

    cache.addLogLine("Line 1");
    cache.addLogLine("Line 2");
    Assertions.assertEquals(2, cache.cachedLogMessages().size());

    cache.logCacheSize(0);
    Assertions.assertEquals(0, cache.cachedLogMessages().size());

    cache.addLogLine("Line 3");
    Assertions.assertEquals(0, cache.cachedLogMessages().size());
  }
}
