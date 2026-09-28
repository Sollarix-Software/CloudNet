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
    String input = "&cRed &aGreen &rReset";
    String colored = ConsoleColor.toColoredString('&', input);

    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.DEFAULT.ansiCode()));
    Assertions.assertTrue(colored.contains("Red "));
    Assertions.assertTrue(colored.contains("Green "));
    Assertions.assertTrue(colored.contains("Reset"));
  }

  @Test
  void testToColoredStringRGB() {
    String input = "&#ff0000Red RGB";
    String colored = ConsoleColor.toColoredString('&', input);

    Assertions.assertEquals("\u001B[38;2;255;0;0mRed RGB", colored);
  }

  @Test
  void testToColoredStringNoTriggerChar() {
    String input = "Plain string without formatting";
    String colored = ConsoleColor.toColoredString('&', input);

    Assertions.assertEquals(input, colored);
  }

  @Test
  void testStripColorStandardAndRGB() {
    String input = "&cRed &#123456RGB &aGreen";
    String stripped = ConsoleColor.stripColor('&', input);

    Assertions.assertEquals("Red RGB Green", stripped);
  }

  @Test
  void testStripColorNoTriggerChar() {
    String input = "Plain text";
    String stripped = ConsoleColor.stripColor('&', input);

    Assertions.assertEquals("Plain text", stripped);
  }

  @Test
  void testByChar() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
  }

  @Test
  void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello &z"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
