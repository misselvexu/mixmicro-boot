/*
 * Copyright (c) 2018 VOPEN.XYZ
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package xyz.vopen.framework.boot.common.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * list tools
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-20 11:25
 */
public class ListTools {
  /**
   * value converter to list
   *
   * @param value
   * @return
   */
  public static List<String> convertToList(Object value) {
    List<String> resourceUrls = new ArrayList<>();
    if (value instanceof List) {
      resourceUrls.addAll((List) value);
    } else {
      resourceUrls.add(String.valueOf(value));
    }
    return resourceUrls;
  }
}
