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
    var raw = "&cHello &aWorld&r!";
    var colored = ConsoleColor.toColoredString('&', raw);
    var expected = ConsoleColor.RED.ansiCode() + "Hello "
      + ConsoleColor.LIGHT_GREEN.ansiCode() + "World"
      + ConsoleColor.DEFAULT.ansiCode() + "!";
    Assertions.assertEquals(expected, colored);
  }

  @Test
  void testToColoredStringRGBColors() {
    var raw = "&#ff0000Red &#00ff00Green";
    var colored = ConsoleColor.toColoredString('&', raw);
    var expected = "\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen";
    Assertions.assertEquals(expected, colored);
  }

  @Test
  void testToColoredStringWithoutColors() {
    var raw = "Hello plain world!";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals(raw, colored);
  }

  @Test
  void testStripColorStandardAndRGB() {
    var raw = "&cHello &#123456World&r!";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals("Hello World!", stripped);
  }

  @Test
  void testStripColorWithoutColors() {
    var raw = "Hello plain world!";
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
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Text ending in &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Text without color"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "&"));
  }
}
