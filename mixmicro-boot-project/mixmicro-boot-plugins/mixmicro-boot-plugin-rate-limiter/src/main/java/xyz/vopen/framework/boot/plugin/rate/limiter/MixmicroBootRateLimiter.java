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

package xyz.vopen.framework.boot.plugin.rate.limiter;

import xyz.vopen.framework.boot.plugin.rate.limiter.support.AbstractRateLimiter;
import xyz.vopen.framework.boot.plugin.rate.limiter.support.GoogleGuavaRateLimiter;
import xyz.vopen.framework.boot.plugin.rate.limiter.support.RedisLuaRateLimiter;

/**
 * Definition of current limiting interface
 *
 * <p>a unified method for defining current limiting needs
 *
 * <p>Basic Abstract implementation class {@link
 * AbstractRateLimiter}
 *
 * <p>Current restriction of token bucket mode of single application provided by Google {@link
 * GoogleGuavaRateLimiter}
 *
 * <p>Implementation of redis Lua script for microservices and distributed applications {@link
 * RedisLuaRateLimiter}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootRateLimiter {
  /**
   * Attempt to obtain a request current limit token
   *
   * @param QPS queries per second
   * @param requestKey request key
   * @return true : allow access to
   */
  boolean tryAcquire(Double QPS, String requestKey);
}
