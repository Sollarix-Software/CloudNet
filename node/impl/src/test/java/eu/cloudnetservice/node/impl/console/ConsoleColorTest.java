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
  void testToColoredStringWithoutTriggerChar() {
    var input = "Hello world, this string has no colors!";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertSame(input, result);
  }

  @Test
  void testToColoredStringWithLegacyColors() {
    var input = "&cRed &aGreen &fWhite";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(result.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(result.contains(ConsoleColor.WHITE.ansiCode()));
    Assertions.assertTrue(result.contains("Red "));
    Assertions.assertTrue(result.contains("Green "));
    Assertions.assertTrue(result.contains("White"));
  }

  @Test
  void testToColoredStringWithRgbColor() {
    var input = "&#ff0000HexRed";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains("\u001B[38;2;255;0;0mHexRed"));
  }

  @Test
  void testStripColorWithoutTriggerChar() {
    var input = "Plain string without formatting";
    var result = ConsoleColor.stripColor('&', input);
    Assertions.assertSame(input, result);
  }

  @Test
  void testStripColorWithLegacyColors() {
    var input = "&cRed &aGreen &fWhite";
    var result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Green White", result);
  }

  @Test
  void testStripColorWithRgbColor() {
    var input = "&#ff0000HexRed";
    var result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("HexRed", result);
  }

  @Test
  void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello World &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello World"));
  }
}
