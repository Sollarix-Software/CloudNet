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
  void testByChar() {
    Assertions.assertEquals(ConsoleColor.BLACK, ConsoleColor.byChar('0'));
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.DEFAULT, ConsoleColor.byChar('r'));
    Assertions.assertNull(ConsoleColor.byChar('z'));
    Assertions.assertNull(ConsoleColor.byChar((char) 250));
  }

  @Test
  void testLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.lastColor('&', "Test &2  "));
    Assertions.assertNull(ConsoleColor.lastColor('&', "No color"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "&"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "&z"));
  }

  @Test
  void testToColoredStringStandardColors() {
    var raw = "Hello &cWorld&r!";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals("Hello " + ConsoleColor.RED.ansiCode() + "World" + ConsoleColor.DEFAULT.ansiCode() + "!", colored);

    var noTrigger = "Hello World!";
    Assertions.assertSame(noTrigger, ConsoleColor.toColoredString('&', noTrigger));
  }

  @Test
  void testToColoredStringRgbColors() {
    var raw = "&#ff0000Red &#00ff00Green";
    var colored = ConsoleColor.toColoredString('&', raw);
    Assertions.assertEquals("\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen", colored);
  }

  @Test
  void testToColoredStringCustomTrigger() {
    var raw = "$cRed text and $#0000ffBlue";
    var colored = ConsoleColor.toColoredString('$', raw);
    Assertions.assertEquals(ConsoleColor.RED.ansiCode() + "Red text and \u001B[38;2;0;0;255mBlue", colored);
  }

  @Test
  void testStripColor() {
    var raw = "Hello &cWorld&r! &#123456RGB";
    var stripped = ConsoleColor.stripColor('&', raw);
    Assertions.assertEquals("Hello World! RGB", stripped);

    var noTrigger = "Clean text without codes";
    Assertions.assertSame(noTrigger, ConsoleColor.stripColor('&', noTrigger));
  }

  @Test
  void testStripColorCustomTrigger() {
    var raw = "%aGreen %bAqua %#abcdefTest";
    var stripped = ConsoleColor.stripColor('%', raw);
    Assertions.assertEquals("Green Aqua Test", stripped);
  }
}
