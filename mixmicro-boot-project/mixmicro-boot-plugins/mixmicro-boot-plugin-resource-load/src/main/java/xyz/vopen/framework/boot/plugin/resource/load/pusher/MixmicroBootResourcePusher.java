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

package xyz.vopen.framework.boot.plugin.resource.load.pusher;

import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.resource.load.pusher.support.MixmicroBootJdbcResourcePusher;
import xyz.vopen.framework.boot.plugin.resource.load.pusher.support.MixmicroBootRedisResourcePusher;

import java.lang.reflect.Method;

/**
 * Mixmicro Boot Resource Pusher Interface
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-19 09:32
 * @see MixmicroBootJdbcResourcePusher
 * @see MixmicroBootRedisResourcePusher
 */
public interface MixmicroBootResourcePusher {
  /**
   * Push resource to result field
   *
   * @param declaredMethod declared method
   * @param methodExecuteResult method execute result
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void loadResource(Method declaredMethod, Object methodExecuteResult) throws MixmicroBootException;

  /**
   * Pull resource from param
   *
   * @param declaredMethod declared method
   * @param param method param array
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void insertResource(Method declaredMethod, Object[] param) throws MixmicroBootException;

  /**
   * Delete resource
   *
   * @param declaredMethod declared method
   * @param param method param array
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void deleteResource(Method declaredMethod, Object[] param) throws MixmicroBootException;

  /**
   * Update resource
   *
   * @param declaredMethod declared method
   * @param param method param array
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  void updateResource(Method declaredMethod, Object[] param) throws MixmicroBootException;
}
