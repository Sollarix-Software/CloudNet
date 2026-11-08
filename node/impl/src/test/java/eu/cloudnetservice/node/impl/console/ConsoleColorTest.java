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
    var raw = "&aHello &cWorld&r!";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertEquals(
      ConsoleColor.LIGHT_GREEN.ansiCode() + "Hello "
        + ConsoleColor.RED.ansiCode() + "World"
        + ConsoleColor.DEFAULT.ansiCode() + "!",
      colored
    );
  }

  @Test
  void testToColoredStringRgbColors() {
    var raw = "&#ff5555Red &#00AAFFBlue &#123456Hex";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertEquals(
      "\u001B[38;2;255;85;85mRed \u001B[38;2;0;170;255mBlue \u001B[38;2;18;52;86mHex",
      colored
    );
  }

  @Test
  void testToColoredStringMixedColors() {
    var raw = "&aGreen &#ff0000Red &rReset";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertEquals(
      ConsoleColor.LIGHT_GREEN.ansiCode() + "Green \u001B[38;2;255;0;0mRed " + ConsoleColor.DEFAULT.ansiCode() + "Reset",
      colored
    );
  }

  @Test
  void testToColoredStringInvalidRgbIgnored() {
    var raw = "&#xyz123NotHex &#123Short";
    var colored = ConsoleColor.toColoredString('&', raw);

    // Invalid hex digits or incomplete hex strings should remain untouched
    Assertions.assertEquals(raw, colored);
  }

  @Test
  void testToColoredStringNoTriggerChar() {
    var raw = "Plain text without any colors.";
    var colored = ConsoleColor.toColoredString('&', raw);

    Assertions.assertEquals(raw, colored);
  }

  @Test
  void testStripColorStandard() {
    var raw = "&aHello &cWorld&r!";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertEquals("Hello World!", stripped);
  }

  @Test
  void testStripColorRgb() {
    var raw = "&#ff5555Red &#00AAFFBlue &#123456Hex";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertEquals("Red Blue Hex", stripped);
  }

  @Test
  void testStripColorMixed() {
    var raw = "&aGreen &#ff0000Red &rReset";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertEquals("Green Red Reset", stripped);
  }

  @Test
  void testStripColorNoTriggerChar() {
    var raw = "Plain text without any colors.";
    var stripped = ConsoleColor.stripColor('&', raw);

    Assertions.assertEquals(raw, stripped);
  }

  @Test
  void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.byChar('a'));
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello &z"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
