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
package xyz.vopen.framework.boot.plugin.resource.load;

import xyz.vopen.framework.boot.common.exception.MixmicroBootException;

import java.util.List;

/**
 * Mixmicro Boot Resource Load 数据驱动委托类 该接口实现类用于查询资源信息
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-12 11:47
 */
public interface MixmicroBootResourceStoreDelegate {
  /**
   * Load Resource Urls
   *
   * @param sourceFieldValue 资源所属业务字段的值
   * @param resourceType 资源类型
   * @return Resource urls
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  List<String> loadResourceUrl(String sourceFieldValue, String resourceType)
      throws MixmicroBootException;

  /**
   * Add Resource Urls
   *
   * @param sourceFieldValue source field value
   * @param resourceType resource type
   * @param resourceUrls resource urls
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void addResource(String sourceFieldValue, String resourceType, List<String> resourceUrls)
      throws MixmicroBootException;

  /**
   * Delete resource urls
   *
   * @param sourceFieldValue source field value
   * @param resourceType resource type
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void deleteResource(String sourceFieldValue, String resourceType) throws MixmicroBootException;

  /**
   * Update resource urls
   *
   * @param sourceFieldValue source field value
   * @param resourceType resource type
   * @param resourceUrls resource urls
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void updateResource(String sourceFieldValue, String resourceType, List<String> resourceUrls)
      throws MixmicroBootException;
}
