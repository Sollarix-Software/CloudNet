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
  void testToColoredStringWithoutTrigger() {
    var input = "Hello world without trigger";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals(input, result);
  }

  @Test
  void testToColoredStringWithLegacyColor() {
    var input = "&cRed Text";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains("Red Text"));
    Assertions.assertFalse(result.contains("&c"));
    Assertions.assertEquals(ConsoleColor.RED.ansiCode() + "Red Text", result);
  }

  @Test
  void testToColoredStringWithRGBColor() {
    var input = "&#ff0000Hex Red Text";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains("Hex Red Text"));
    Assertions.assertFalse(result.contains("&#ff0000"));
    Assertions.assertEquals("\u001B[38;2;255;0;0mHex Red Text", result);
  }

  @Test
  void testStripColor() {
    var legacyInput = "&aGreen &cRed";
    var strippedLegacy = ConsoleColor.stripColor('&', legacyInput);
    Assertions.assertEquals("Green Red", strippedLegacy);

    var rgbInput = "&#00ff00Green &#ff0000Red";
    var strippedRgb = ConsoleColor.stripColor('&', rgbInput);
    Assertions.assertEquals("Green Red", strippedRgb);
  }

  @Test
  void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello &z"));
  }
}
