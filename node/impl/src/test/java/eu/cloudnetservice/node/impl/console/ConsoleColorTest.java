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
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.byChar('a'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertEquals(ConsoleColor.YELLOW, ConsoleColor.lastColor('&', "Hello &e "));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello &z"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hi"));
  }

  @Test
  public void testToColoredString() {
    var colored = ConsoleColor.toColoredString('&', "&cRed &aGreen &#ff0000RGB");
    Assertions.assertTrue(colored.contains("Red "));
    Assertions.assertTrue(colored.contains("Green "));
    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0mRGB"));
  }

  @Test
  public void testStripColor() {
    var stripped = ConsoleColor.stripColor('&', "&cRed &aGreen &#ff0000RGB");
    Assertions.assertEquals("Red Green RGB", stripped);
  }
}
