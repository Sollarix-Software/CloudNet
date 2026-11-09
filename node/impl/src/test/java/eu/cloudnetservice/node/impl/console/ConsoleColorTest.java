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
  void testToColoredStringLegacy() {
    var raw = "&cHello &aWorld&r";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals(
      ConsoleColor.RED.ansiCode() + "Hello " + ConsoleColor.LIGHT_GREEN.ansiCode() + "World" + ConsoleColor.DEFAULT.ansiCode(),
      colored);
  }

  @Test
  void testToColoredStringRgb() {
    var raw = "&#ff0000Red &#00ff00Green";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals("\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen", colored);
  }

  @Test
  void testToColoredStringCustomTriggerChar() {
    var raw = "§cHello §#0000ffBlue";
    var colored = ConsoleColor.toColoredString('§', raw);
    Assertions.assertEquals(ConsoleColor.RED.ansiCode() + "Hello \u001B[38;2;0;0;255mBlue", colored);
  }

  @Test
  void testToColoredStringWithoutColors() {
    var raw = "Just a plain message without formatting";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals(raw, colored);
  }

  @Test
  void testStripColorLegacy() {
    var raw = "&cHello &aWorld&r!";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals("Hello World!", stripped);
  }

  @Test
  void testStripColorRgb() {
    var raw = "&#ff00aaRainbow &#123456Text";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals("Rainbow Text", stripped);
  }

  @Test
  void testStripColorCustomTriggerChar() {
    var raw = "§#abcdefCustom §eTrigger";
    var stripped = ConsoleColor.stripColor('§', raw);
    Assertions.assertEquals("Custom Trigger", stripped);
  }

  @Test
  void testStripColorWithoutColors() {
    var raw = "No colors here";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals(raw, stripped);
  }
}
