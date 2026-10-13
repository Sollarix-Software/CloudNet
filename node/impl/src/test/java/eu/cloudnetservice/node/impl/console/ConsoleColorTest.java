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
  void testToColoredStringWithStandardColors() {
    String input = "&cRed &aGreen &bAqua";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals(
      ConsoleColor.RED.ansiCode() + "Red " + ConsoleColor.LIGHT_GREEN.ansiCode() + "Green " + ConsoleColor.AQUA.ansiCode() + "Aqua",
      result
    );
  }

  @Test
  void testToColoredStringWithRGBColors() {
    String input = "&#FF0000Red &#00FF00Green";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertEquals(
      "\u001B[38;2;255;0;0mRed \u001B[38;2;0;255;0mGreen",
      result
    );
  }

  @Test
  void testStripColor() {
    String input = "&cRed &#00FF00Green text";
    String result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Green text", result);
  }

  @Test
  void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
