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
    String input = "Hello World 123";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertSame(input, result);
  }

  @Test
  public void testStripColorWithoutTriggerChar() {
    String input = "Plain message without colors";
    String result = ConsoleColor.stripColor('&', input);
    Assertions.assertSame(input, result);
  }

  @Test
  public void testToColoredStringWithStandardColor() {
    String input = "&cRed Text &rDefault";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains(ConsoleColor.RED.ansiCode()));
    Assertions.assertTrue(result.contains(ConsoleColor.DEFAULT.ansiCode()));
  }

  @Test
  public void testStripColorWithStandardColor() {
    String input = "&cRed Text &rDefault";
    String result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Text Default", result);
  }

  @Test
  public void testToColoredStringWithRgbColor() {
    String input = "&#FF0000Red Text";
    String result = ConsoleColor.toColoredString('&', input);
    Assertions.assertTrue(result.contains("\u001B[38;2;255;0;0m"));
  }

  @Test
  public void testStripColorWithRgbColor() {
    String input = "&#FF0000Red Text";
    String result = ConsoleColor.stripColor('&', input);
    Assertions.assertEquals("Red Text", result);
  }

  @Test
  public void testCustomTriggerChar() {
    String input = "$cRed Text";
    String result = ConsoleColor.toColoredString('$', input);
    Assertions.assertTrue(result.contains(ConsoleColor.RED.ansiCode()));

    String stripped = ConsoleColor.stripColor('$', input);
    Assertions.assertEquals("Red Text", stripped);
  }
}
