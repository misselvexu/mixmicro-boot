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

package xyz.vopen.framework.boot.plugin.http.converter.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import xyz.vopen.framework.boot.plugin.http.converter.filter.annotation.MixmicroBootDecimalAccuracy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * Mixmicro Boot Decimal Accuracy Value Filter Perform formatting if {@link MixmicroBootDecimalAccuracy} is
 * added to the field and the type is {@link BigDecimal}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class DecimalAccuracyFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    try {
      // find field
      Field field = ReflectionUtils.findField(object.getClass(), name);
      // Have ApiBootDecimalAccuracy Annotation
      // Value is BigDecimal Instance
      if (field.isAnnotationPresent(MixmicroBootDecimalAccuracy.class) && value instanceof BigDecimal) {
        MixmicroBootDecimalAccuracy decimalAccuracy =
            field.getDeclaredAnnotation(MixmicroBootDecimalAccuracy.class);
        BigDecimal decimalValue = (BigDecimal) value;
        return decimalValue.setScale(decimalAccuracy.scale(), decimalAccuracy.roundingMode());
      }
    } catch (Exception e) {
      // ignore
      return value;
    }
    return value;
  }
}
