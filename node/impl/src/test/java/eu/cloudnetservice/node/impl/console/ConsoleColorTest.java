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
    var input = "Hello World!";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals(input, result);
  }

  @Test
  public void testToColoredStringWithStandardColor() {
    var input = "&cRed Text";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals(ConsoleColor.RED.ansiCode() + "Red Text", result);
  }

  @Test
  public void testToColoredStringWithRgbColor() {
    var input = "&#ff0000Red Text";
    var result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals("\u001B[38;2;255;0;0mRed Text", result);
  }

  @Test
  public void testStripColorWithoutTriggerChar() {
    var input = "Hello World!";
    var result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals(input, result);
  }

  @Test
  public void testStripColorWithStandardAndRgbColor() {
    var input = "&aGreen &#00ff00Text &rReset";
    var result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Green Text Reset", result);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.lastColor('&', "Hello &a"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello World"));
  }
}
