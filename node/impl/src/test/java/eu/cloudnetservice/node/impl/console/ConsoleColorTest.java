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
    var plain = "Hello World! No colors here.";
    Assertions.assertSame(plain, ConsoleColor.toColoredString('&', plain));
  }

  @Test
  public void testToColoredStringWithStandardColor() {
    var input = "&cRed Text";
    var colored = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(colored.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(colored.contains("Red Text"));
  }

  @Test
  public void testToColoredStringWithRgbColor() {
    var input = "&#FF0000Red RGB";
    var colored = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(colored.contains("\u001B[38;2;255;0;0m"));
    Assertions.assertTrue(colored.contains("Red RGB"));
  }

  @Test
  public void testStripColorWithoutTriggerChar() {
    var plain = "Hello World! No colors here.";
    Assertions.assertSame(plain, ConsoleColor.stripColor('&', plain));
  }

  @Test
  public void testStripColorWithStandardAndRgbColor() {
    var input = "&cRed &#00FF00Green &aLightGreen";
    var stripped = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Green LightGreen", stripped);
  }

  @Test
  public void testByCharAndLastColor() {
    Assertions.assertEquals(ConsoleColor.RED, ConsoleColor.byChar('c'));
    Assertions.assertEquals(ConsoleColor.GREEN, ConsoleColor.lastColor('&', "Hello &2"));
    Assertions.assertNull(ConsoleColor.lastColor('&', "Hello World"));
  }
}
