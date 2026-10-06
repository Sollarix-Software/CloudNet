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
    var plain = "Hello world without any colors!";
    Assertions.assertSame(plain, ConsoleColor.toColoredString('&', plain));
  }

  @Test
  public void testToColoredStringWithStandardColor() {
    var colored = ConsoleColor.toColoredString('&', "&aHello &cWorld");
    Assertions.assertTrue(colored.contains("Hello "));
    Assertions.assertTrue(colored.contains("World"));
    Assertions.assertFalse(colored.contains("&a"));
    Assertions.assertFalse(colored.contains("&c"));
  }

  @Test
  public void testToColoredStringWithRgbColor() {
    var colored = ConsoleColor.toColoredString('&', "&#FF0000Red Text");
    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(colored.contains("Red Text"));
    Assertions.assertFalse(colored.contains("&#FF0000"));
  }

  @Test
  public void testStripColorWithoutTriggerChar() {
    var plain = "Hello world without any colors!";
    Assertions.assertSame(plain, ConsoleColor.stripColor('&', plain));
  }

  @Test
  public void testStripColorWithStandardAndRgbColor() {
    var input = "&aHello &#00FF00Green World &rReset";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Hello Green World Reset", stripped);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.LIGHT_GREEN, ConsoleColor.byChar('a'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.byChar('2'));
    Assertions.assertNull(ConsoleColor.byChar('z'));

    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.lastColor('&', "Hello &c"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello World"));
  }
}
