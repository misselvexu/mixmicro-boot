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

import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.resource.load.MixmicroBootResourceStoreDelegate;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Mixmicro Boot Jdbc Resource Pusher
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-19 09:33
 */
public class MixmicroBootJdbcResourcePusher extends MixmicroBootAbstractResourcePusher {
  /** Mixmicro Boot Resource Store Delegate Use load resource url from jdbc */
  private MixmicroBootResourceStoreDelegate mixmicroBootResourceStoreDelegate;

  public MixmicroBootJdbcResourcePusher(MixmicroBootResourceStoreDelegate mixmicroBootResourceStoreDelegate) {
    this.mixmicroBootResourceStoreDelegate = mixmicroBootResourceStoreDelegate;
    if (ObjectUtils.isEmpty(this.mixmicroBootResourceStoreDelegate)) {
      throw new MixmicroBootException(
          "Unable to load [ApiBootResourceStoreDelegate] implementation class instance");
    }
  }

  /**
   * load resource from ApiBootResourceStoreDelegate#loadResourceUrl method
   *
   * @param declaredMethod declared method
   * @param sourceFieldValue sourceFieldValue
   * @param resourceType resourceType
   * @return resource List
   * @see MixmicroBootResourceStoreDelegate
   */
  @Override
  public List<String> loadResourceUrl(
      Method declaredMethod, String sourceFieldValue, String resourceType) {
    return mixmicroBootResourceStoreDelegate.loadResourceUrl(sourceFieldValue, resourceType);
  }

  /**
   * delete resource urls
   *
   * @param declaredMethod declared method
   * @param sourceFieldValue sourceFieldValue
   * @param resourceType resourceType
   */
  @Override
  public void deleteResourceUrl(
      Method declaredMethod, String sourceFieldValue, String resourceType) {
    mixmicroBootResourceStoreDelegate.deleteResource(sourceFieldValue, resourceType);
  }

  /**
   * insert resource urls
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
    mixmicroBootResourceStoreDelegate.addResource(sourceFieldValue, resourceType, resourceUrls);
  }

  /**
   * update resource urls
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
    mixmicroBootResourceStoreDelegate.updateResource(sourceFieldValue, resourceType, resourceUrls);
  }
}
