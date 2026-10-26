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
  public void testToColoredStringWithoutTriggerChar() {
    var plainText = "Hello World, no colors here!";
    Assertions.assertEquals(plainText, ConsoleColor.toColoredString('&', plainText));
  }

  @Test
  public void testToColoredStringWithStandardColors() {
    var input = "&aGreen &cRed &rReset";
    var colored = ConsoleColor.toColoredString('&', input);
    Assertions.assertNotEquals(input, colored);
    Assertions.assertTrue(colored.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.DEFAULT.ansiCode()));
  }

  @Test
  public void testToColoredStringWithRGBColors() {
    var input = "&#ff0000RedText";
    var colored = ConsoleColor.toColoredString('&', input);
    Assertions.assertFalse(colored.contains("&#ff0000"));
    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(colored.contains("RedText"));
  }

  @Test
  public void testStripColorWithoutTriggerChar() {
    var plainText = "Plain text without colors";
    Assertions.assertEquals(plainText, ConsoleColor.stripColor('&', plainText));
  }

  @Test
  public void testStripColorWithStandardColors() {
    var input = "&aGreen &cRed &rReset";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Green Red Reset", stripped);
  }

  @Test
  public void testStripColorWithRGBColors() {
    var input = "&#ff0000RedText and &aGreenText";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("RedText and GreenText", stripped);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello World &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello World"));
  }
}
