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

class ConsoleColorTest {

  @Test
  void testToColoredStringStandardColors() {
    var raw = "&cHello &fWorld&r!";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.WHITE.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.DEFAULT.ansiCode()));
    Assertions.assertTrue(colored.contains("Hello "));
    Assertions.assertTrue(colored.contains("World"));
  }

  @Test
  void testToColoredStringFastPath() {
    var raw = "Hello World, no colors here!";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertSame(raw, colored);
  }

  @Test
  void testToColoredStringRgb() {
    var raw = "&#ff0000Red Text";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(colored.contains("Red Text"));
  }

  @Test
  void testStripColorStandard() {
    var raw = "&cHello &fWorld&r!";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertEquals("Hello World!", stripped);
  }

  @Test
  void testStripColorFastPath() {
    var raw = "Hello plain text!";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertSame(raw, stripped);
  }

  @Test
  void testStripColorRgb() {
    var raw = "&#123456RGB colored text";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertEquals("RGB colored text", stripped);
  }

  @Test
  void testByChar() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
  }

  @Test
  void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Test message &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Test message"));
  }
}
