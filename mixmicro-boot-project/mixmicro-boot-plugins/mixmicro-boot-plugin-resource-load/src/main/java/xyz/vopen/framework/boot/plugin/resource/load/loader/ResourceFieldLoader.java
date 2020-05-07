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

package xyz.vopen.framework.boot.plugin.resource.load.loader;

import xyz.vopen.framework.boot.plugin.resource.load.annotation.ResourceField;
import xyz.vopen.framework.boot.plugin.resource.load.annotation.ResourceFields;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mixmicro Boot Resource Field Annotation Loader
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-12 15:44
 */
public class ResourceFieldLoader {

  /**
   * load method declared ResourceField Annotation List
   *
   * @param method declared method
   * @return ResourceField Annotation List
   */
  public static List<ResourceField> getDeclaredResourceField(Method method) {
    List<ResourceField> resourceFieldList = new ArrayList();
    // single ResourceField Annotation
    ResourceField resourceField = method.getDeclaredAnnotation(ResourceField.class);
    if (!ObjectUtils.isEmpty(resourceField)) {
      resourceFieldList.add(resourceField);
    }

    // multiple ResourceField Annotation
    ResourceFields ResourceFields = method.getDeclaredAnnotation(ResourceFields.class);
    if (!ObjectUtils.isEmpty(ResourceFields)) {
      resourceFieldList.addAll(Arrays.asList(ResourceFields.value()));
    }

    return resourceFieldList;
  }
}
