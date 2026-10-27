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
    var plain = "Hello world!";
    Assertions.assertEquals(plain, ConsoleColor.toColoredString('&', plain));
  }

  @Test
  void testToColoredStringWithLegacyColor() {
    var input = "&aHello &bWorld";
    var expected = ConsoleColor.LIGHT_GREEN.ansiCode() + "Hello " + ConsoleColor.AQUA.ansiCode() + "World";
    Assertions.assertEquals(expected, ConsoleColor.toColoredString('&', input));
  }

  @Test
  void testToColoredStringWithRgbColor() {
    var input = "&#ff0000Red &#00ff00Green";
    var expected = "\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen";
    Assertions.assertEquals(expected, ConsoleColor.toColoredString('&', input));
  }

  @Test
  void testStripColorWithoutTrigger() {
    var plain = "Hello world!";
    Assertions.assertEquals(plain, ConsoleColor.stripColor('&', plain));
  }

  @Test
  void testStripColorWithLegacyColor() {
    var input = "&aHello &bWorld";
    Assertions.assertEquals("Hello World", ConsoleColor.stripColor('&', input));
  }

  @Test
  void testStripColorWithRgbColor() {
    var input = "&#ff0000Red &#00ff00Green";
    Assertions.assertEquals("Red Green", ConsoleColor.stripColor('&', input));
  }

  @Test
  void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.lastColor('&', "Hello &2"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
