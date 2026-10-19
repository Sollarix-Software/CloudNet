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
  public void testToColoredStringWithoutColorCodes() {
    String plain = "Hello, world! No colors here.";
    Assertions.assertEquals(plain, ConsoleColor.toColoredString('&', plain));
  }

  @Test
  public void testToColoredStringWithStandardColor() {
    String colored = ConsoleColor.toColoredString('&', "&aHello &cWorld");
    Assertions.assertTrue(colored.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(colored.contains("Hello"));
    Assertions.assertTrue(colored.contains("World"));
  }

  @Test
  public void testToColoredStringWithRGBColor() {
    String rgbInput = "&#ff0000Red Text";
    String colored = ConsoleColor.toColoredString('&', rgbInput);
    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(colored.contains("Red Text"));
  }

  @Test
  public void testStripColorWithoutColorCodes() {
    String plain = "Plain text without colors.";
    Assertions.assertEquals(plain, ConsoleColor.stripColor('&', plain));
  }

  @Test
  public void testStripColorWithStandardAndRGBColor() {
    String input = "&aHello &#ff0000World&r!";
    String stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Hello World!", stripped);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.byChar('a'));
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Test message &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Test message without color"));
  }
}
