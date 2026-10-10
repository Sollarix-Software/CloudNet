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
  void testByChar() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertEquals(ConsoleColor.DEFAULT, ConsoleColor.byChar('r'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
  }

  @Test
  void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &cWorld &c"));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.lastColor('&', "Test &2"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Test without color"));
  }

  @Test
  void testToColoredStringWithoutTriggerChar() {
    String plain = "Hello world! 123";
    Assertions.assertSame(plain, ConsoleColor.toColoredString('&', plain));
  }

  @Test
  void testToColoredStringWithStandardColor() {
    String input = "&cRed Text &2Green Text";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains("Red Text"));
    Assertions.assertTrue(result.contains("Green Text"));
    Assertions.assertFalse(result.contains("&c"));
    Assertions.assertFalse(result.contains("&2"));
  }

  @Test
  void testToColoredStringWithRgbColor() {
    String input = "&#FF0000Hex Red";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains("\u001B[38;2;255;0;0mHex Red"));

    // Custom trigger character
    String customInput = "$#00FF00Hex Green";
    String customResult = ConsoleColor.toColoredString('$', customInput);
    Assertions.assertTrue(customResult.contains("\u001B[38;2;0;255;0mHex Green"));
  }

  @Test
  void testStripColorWithoutTriggerChar() {
    String plain = "Hello world! 123";
    Assertions.assertSame(plain, ConsoleColor.stripColor('&', plain));
  }

  @Test
  void testStripColorWithStandardAndRgbColors() {
    String input = "&cRed &#FF0000Hex &2Green";
    String stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Hex Green", stripped);
  }
}
