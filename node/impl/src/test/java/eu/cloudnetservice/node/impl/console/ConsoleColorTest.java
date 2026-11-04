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
  void testToColoredStringStandard() {
    var colored = ConsoleColor.toColoredString('&', "&cHello &aWorld&r!");
    Assertions.assertEquals(
      ConsoleColor.RED.ansiCode() + "Hello " + ConsoleColor.LIGHT_GREEN.ansiCode() + "World" + ConsoleColor.DEFAULT.ansiCode() + "!",
      colored);
  }

  @Test
  void testToColoredStringRGB() {
    var colored = ConsoleColor.toColoredString('&', "&#FF0000Red Text&#00FF00Green Text");
    Assertions.assertEquals("\u001B[38;2;255;0;0mRed Text\u001B[38;2;0;255;0mGreen Text", colored);

    // Custom trigger character
    var customColored = ConsoleColor.toColoredString('§', "§#0000FFBlue Text");
    Assertions.assertEquals("\u001B[38;2;0;0;255mBlue Text", customColored);
  }

  @Test
  void testStripColor() {
    var stripped = ConsoleColor.stripColor('&', "&cHello &#123456RGB &aWorld&r!");
    Assertions.assertEquals("Hello RGB World!", stripped);

    var strippedCustom = ConsoleColor.stripColor('§', "§cHello §#ABCDEFWorld§r");
    Assertions.assertEquals("Hello World", strippedCustom);
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
