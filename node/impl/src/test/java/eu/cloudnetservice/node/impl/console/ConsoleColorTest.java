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
  public void testToColoredStringNoTriggerChar() {
    var input = "Hello, world! 123";
    Assertions.assertEquals(input, ConsoleColor.toColoredString('&', input));
  }

  @Test
  public void testToColoredStringStandardColors() {
    var input = "&aHello &cworld!";
    var expected = ConsoleColor.LIGHT_GREEN.ansiCode() + "Hello " + ConsoleColor.RED.ansiCode() + "world!";
    Assertions.assertEquals(expected, ConsoleColor.toColoredString('&', input));
  }

  @Test
  public void testToColoredStringRGBColors() {
    var input = "&#ff0000Red &#00ff00Green";
    var expected = "\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen";
    Assertions.assertEquals(expected, ConsoleColor.toColoredString('&', input));
  }

  @Test
  public void testToColoredStringMixedColors() {
    var input = "&aGreen &#0000ffBlue &rReset";
    var expected = ConsoleColor.LIGHT_GREEN.ansiCode() + "Green \u001B[38;2;0;0;255mBlue "
      + ConsoleColor.DEFAULT.ansiCode() + "Reset";
    Assertions.assertEquals(expected, ConsoleColor.toColoredString('&', input));
  }

  @Test
  public void testStripColorNoTriggerChar() {
    var input = "Hello, world! 123";
    Assertions.assertEquals(input, ConsoleColor.stripColor('&', input));
  }

  @Test
  public void testStripColorStandardColors() {
    var input = "&aHello &cworld!";
    Assertions.assertEquals("Hello world!", ConsoleColor.stripColor('&', input));
  }

  @Test
  public void testStripColorRGBColors() {
    var input = "&#ff0000Red &#00ff00Green";
    Assertions.assertEquals("Red Green", ConsoleColor.stripColor('&', input));
  }

  @Test
  public void testStripColorMixed() {
    var input = "&aGreen &#0000ffBlue &rReset";
    Assertions.assertEquals("Green Blue Reset", ConsoleColor.stripColor('&', input));
  }

  @Test
  public void testByChar() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.byChar('a'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
  }

  @Test
  public void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.lastColor('&', "Text &a  "));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
