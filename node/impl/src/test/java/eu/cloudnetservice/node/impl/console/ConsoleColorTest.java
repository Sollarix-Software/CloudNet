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

package eu.cloudnetservice.node.impl.console;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsoleColorTest {

  @Test
  void testToColoredStringStandardColor() {
    var raw = "&cHello &aWorld&r";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.DEFAULT.ansiCode()));
  }

  @Test
  void testToColoredStringRgbColor() {
    var raw = "&#ff55aaCustom RGB Message";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertTrue(colored.startsWith("\u001B[38;2;255;85;170mCustom RGB Message"));
  }

  @Test
  void testToColoredStringNoTriggerChar() {
    var raw = "Plain text without any colors";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals(raw, colored);
  }

  @Test
  void testStripColorStandardColor() {
    var raw = "&cHello &aWorld&r";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals("Hello World", stripped);
  }

  @Test
  void testStripColorRgbColor() {
    var raw = "&#ff55aaCustom RGB Message";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals("Custom RGB Message", stripped);
  }

  @Test
  void testStripColorNoTriggerChar() {
    var raw = "Plain text without any colors";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals(raw, stripped);
  }

  @Test
  void testByChar() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.DEFAULT, ConsoleColor.byChar('r'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
  }

  @Test
  void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Test message &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Test message"));
  }
}
