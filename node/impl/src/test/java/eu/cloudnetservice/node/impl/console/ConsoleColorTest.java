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
  public void testToColoredString() {
    var colored = ConsoleColor.toColoredString('&', "&aHello &cWorld");
    Assertions.assertTrue(colored.contains("Hello"));
    Assertions.assertTrue(colored.contains("World"));
    Assertions.assertTrue(colored.contains(ConsoleColor.LIGHT_GREEN.ansiCode()));
    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));

    var rgbColored = ConsoleColor.toColoredString('&', "&#FF0000Hello");
    Assertions.assertTrue(rgbColored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(rgbColored.contains("Hello"));
  }

  @Test
  public void testStripColor() {
    var stripped = ConsoleColor.stripColor('&', "&aHello &cWorld");
    Assertions.assertEquals("Hello World", stripped);

    var rgbStripped = ConsoleColor.stripColor('&', "&#FF0000Hello");
    Assertions.assertEquals("Hello", rgbStripped);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.byChar('a'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello"));
  }
}
