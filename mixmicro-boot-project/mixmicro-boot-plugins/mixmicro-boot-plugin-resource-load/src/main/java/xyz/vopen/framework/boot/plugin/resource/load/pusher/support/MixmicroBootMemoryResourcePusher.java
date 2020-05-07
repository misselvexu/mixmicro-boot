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

package xyz.vopen.framework.boot.plugin.resource.load.pusher.support;

import xyz.vopen.framework.boot.plugin.resource.load.MixmicroBootResourceStoreDelegate;
import xyz.vopen.framework.boot.plugin.resource.load.context.MixmicroBootResourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mixmicro Boot Resource Memory Pusher
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-19 11:16
 */
public class MixmicroBootMemoryResourcePusher extends MixmicroBootJdbcResourcePusher {
  /** logger instance */
  static Logger logger = LoggerFactory.getLogger(MixmicroBootMemoryResourcePusher.class);
  /** memory resource urls */
  private Map<String, List<String>> RESOURCE_URLS = new HashMap();

  public MixmicroBootMemoryResourcePusher(MixmicroBootResourceStoreDelegate mixmicroBootResourceStoreDelegate) {
    super(mixmicroBootResourceStoreDelegate);
  }

  /**
   * Check whether the data is cached in memory If not cached, query from the database If cached ,
   * query from the memory
   *
   * @param declaredMethod declared method
   * @param sourceFieldValue sourceFieldValue
   * @param resourceType resourceType
   * @return resource urls
   */
  @Override
  public List<String> loadResourceUrl(
      Method declaredMethod, String sourceFieldValue, String resourceType) {
    if (ObjectUtils.isEmpty(sourceFieldValue) || ObjectUtils.isEmpty(resourceType)) {
      logger.warn(
          "@ResourceField param [source]|[type]|[name] have empty value, load resource urls with memory fail.");
      return Collections.emptyList();
    }
    // formatter key
    String resourceKey =
        MixmicroBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
    // get resource urls
    List<String> resourceUrls = RESOURCE_URLS.get(resourceKey);
    if (ObjectUtils.isEmpty(resourceUrls)) {
      // get resource urls from jdbc
      resourceUrls = super.loadResourceUrl(declaredMethod, sourceFieldValue, resourceType);
      // put resource
      RESOURCE_URLS.put(resourceKey, resourceUrls);
    }
    return resourceUrls;
  }

  /**
   * remove from memory
   *
   * @param declaredMethod declared method
   * @param sourceFieldValue sourceFieldValue
   * @param resourceType resourceType
   */
  @Override
  public void deleteResourceUrl(
      Method declaredMethod, String sourceFieldValue, String resourceType) {
    if (ObjectUtils.isEmpty(sourceFieldValue) || ObjectUtils.isEmpty(resourceType)) {
      logger.warn(
          "@ResourceField param [source]|[type]|[name] have empty value, delete resource urls with memory fail.");
      return;
    }
    // remove from jdbc
    super.deleteResourceUrl(declaredMethod, sourceFieldValue, resourceType);
    // formatter key
    String resourceKey =
        MixmicroBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
    // remove memory resource urls
    RESOURCE_URLS.remove(resourceKey);
  }

  /**
   * insert to memory
   *
   * @param declaredMethod declared method
   * @param sourceFieldValue sourceFieldValue
   * @param resourceType resourceType
   * @param resourceUrls resource urls
   */
  @Override
  public void insertResourceUrl(
      Method declaredMethod,
      String sourceFieldValue,
      String resourceType,
      List<String> resourceUrls) {
    if (ObjectUtils.isEmpty(sourceFieldValue)
        || ObjectUtils.isEmpty(resourceType)
        || ObjectUtils.isEmpty(resourceUrls)) {
      logger.warn(
          "@ResourceField param [source]|[type]|[name] have empty value, insert resource urls with memory fail.");
      return;
    }
    super.insertResourceUrl(declaredMethod, sourceFieldValue, resourceType, resourceUrls);
    // formatter key
    String resourceKey =
        MixmicroBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
    // remove memory resource urls
    RESOURCE_URLS.put(resourceKey, resourceUrls);
  }

  /**
   * update memory data
   *
   * @param declaredMethod declared method
   * @param sourceFieldValue sourceFieldValue
   * @param resourceType resourceType
   * @param resourceUrls resource urls
   */
  @Override
  public void updateResourceUrl(
      Method declaredMethod,
      String sourceFieldValue,
      String resourceType,
      List<String> resourceUrls) {
    if (ObjectUtils.isEmpty(sourceFieldValue)
        || ObjectUtils.isEmpty(resourceType)
        || ObjectUtils.isEmpty(resourceUrls)) {
      logger.warn(
          "@ResourceField param [source]|[type]|[name] have empty value, update resource urls with memory fail.");
      return;
    }
    super.updateResourceUrl(declaredMethod, sourceFieldValue, resourceType, resourceUrls);

    // execute delete resource urls
    deleteResourceUrl(declaredMethod, sourceFieldValue, resourceType);

    // execute insert resource urls
    insertResourceUrl(declaredMethod, sourceFieldValue, resourceType, resourceUrls);
  }
}
