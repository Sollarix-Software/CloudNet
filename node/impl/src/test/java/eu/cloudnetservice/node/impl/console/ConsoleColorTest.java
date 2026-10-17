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
  void testToColoredStringStandardColors() {
    var input = "&aHello &cWorld";
    var colored = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(colored.contains("Hello "));
    Assertions.assertTrue(colored.contains("World"));
    Assertions.assertFalse(colored.contains("&a"));
    Assertions.assertFalse(colored.contains("&c"));
  }

  @Test
  void testToColoredStringRGBColors() {
    var input = "&#ff0000Red Text";
    var colored = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(colored.contains("Red Text"));

    // Custom trigger char
    var inputCustom = "$#00ff00Green Text";
    var coloredCustom = ConsoleColor.toColoredString('$', inputCustom);
    Assertions.assertTrue(coloredCustom.contains("\u001B[38;2;0;255;0m"));
    Assertions.assertTrue(coloredCustom.contains("Green Text"));
  }

  @Test
  void testStripColorStandardAndRGB() {
    var input = "&aHello &#ff0000World&r!";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Hello World!", stripped);

    var inputNoColors = "Plain Text";
    Assertions.assertEquals("Plain Text", ConsoleColor.stripColor('&', inputNoColors));
  }

  @Test
  void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Test message &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Test message without color"));
  }
}
