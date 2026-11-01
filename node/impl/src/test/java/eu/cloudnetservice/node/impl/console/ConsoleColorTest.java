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
  public void testColorConversion() {
    var rawText = "&cHello &aWorld";
    var colored = ConsoleColor.toColoredString('&', rawText);
    Assertions.assertEquals(ConsoleColor.RED.ansiCode() + "Hello " + ConsoleColor.LIGHT_GREEN.ansiCode() + "World", colored);
  }

  @Test
  public void testRGBColorConversion() {
    var rawText = "&#ff0000Red &#00ff00Green";
    var colored = ConsoleColor.toColoredString('&', rawText);
    var expected = "\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen";
    Assertions.assertEquals(expected, colored);
  }

  @Test
  public void testStripColor() {
    var rawText = "&cHello &aWorld &#ff0000RGB";
    var stripped = ConsoleColor.stripColor('&', rawText);
    Assertions.assertEquals("Hello World RGB", stripped);
  }

  @Test
  public void testCustomTriggerChar() {
    var rawText = "§cHello §#0000ffBlue";
    var colored = ConsoleColor.toColoredString('§', rawText);
    var expected = ConsoleColor.RED.ansiCode() + "Hello \u001B[38;2;0;0;255mBlue";
    Assertions.assertEquals(expected, colored);

    var stripped = ConsoleColor.stripColor('§', rawText);
    Assertions.assertEquals("Hello Blue", stripped);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.DEFAULT, ConsoleColor.byChar('r'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
