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
  void testToColoredStringWithoutColorCodes() {
    var plain = "This is a plain test string without colors";
    var colored = ConsoleColor.toColoredString('&', plain);
    Assertions.assertSame(plain, colored);
  }

  @Test
  void testToColoredStringWithStandardColor() {
    var input = "&cHello &aWorld&r";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(result.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(result.contains(ConsoleColor.DEFAULT.ansiCode()));
  }

  @Test
  void testToColoredStringWithRgbColor() {
    var input = "&#ff0000Red RGB &#00ff00Green RGB";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals("\u001B[38;2;255;0;0mRed RGB \u001B[38;2;0;255;0mGreen RGB", result);
  }

  @Test
  void testStripColorWithoutColorCodes() {
    var plain = "This is a plain test string without colors";
    var stripped = ConsoleColor.stripColor('&', plain);
    Assertions.assertSame(plain, stripped);
  }

  @Test
  void testStripColorWithStandardColors() {
    var input = "&cHello &aWorld&r!";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Hello World!", stripped);
  }

  @Test
  void testStripColorWithRgbColors() {
    var input = "&#ff0000Red RGB &#00ff00Green RGB!";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red RGB Green RGB!", stripped);
  }

  @Test
  void testStripColorWithMixedColors() {
    var input = "&#ff0000Red &lBold &#00ff00Green!";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Bold Green!", stripped);
  }

  @Test
  void testByChar() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.BLACK, ConsoleColor.byChar('0'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
  }

  @Test
  void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello &z"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
