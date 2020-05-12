/*
 * Copyright (c) 2018 VOPEN.XYZ
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package xyz.vopen.framework.boot.plugin.security.delegate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.Serializable;

/**
 * Data repository delegation interface
 *
 * <p>To integrate SpringSecurity with Mixmicro, you need to query the agent class of <code>
 * {@link UserDetails}</code> Use <code>{@link MixmicroBootDefaultStoreDelegate}</code> by default
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootStoreDelegate extends Serializable {
  /**
   * Query user
   *
   * <p>Users use to authenticate SpringSecurity When we use JDBC to store users, we need to query
   * {@link UserDetails} from the database according to the {@link UserDetails#getUsername()} If
   * oauth2 is integrated, it corresponds to the {@link UserDetails#getUsername()} and {@link
   * UserDetails#getPassword()} of "grant_type=password" authorization mode
   *
   * <p>It should be noted that： do not use this method in memory mode
   *
   * @param username {@link UserDetails#getUsername()}
   * @return {@link UserDetails}
   * @throws UsernameNotFoundException Throw this exception if the user does not exist
   */
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
