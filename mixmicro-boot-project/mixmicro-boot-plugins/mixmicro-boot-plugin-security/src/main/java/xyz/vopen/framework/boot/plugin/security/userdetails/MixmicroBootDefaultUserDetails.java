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

package xyz.vopen.framework.boot.plugin.security.userdetails;

import lombok.Data;
import xyz.vopen.framework.boot.common.enums.YesOrNo;
import xyz.vopen.framework.boot.plugin.security.jdbc.MixmicroBootDefaultUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.vopen.framework.boot.plugin.security.delegate.MixmicroBootDefaultStoreDelegate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The default {@link UserDetails} implement class
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @see
 *     MixmicroBootDefaultStoreDelegate#loadUserByUsername(String)
 */
@Data
public class MixmicroBootDefaultUserDetails extends MixmicroBootDefaultUserEntity implements UserDetails {
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList() {
      {
        add((GrantedAuthority) () -> "ROLE_USER");
      }
    };
  }

  @Override
  public String getPassword() {
    return super.getUiPassword();
  }

  @Override
  public String getUsername() {
    return super.getUiUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return YesOrNo.NO.getValue().equals(super.getUiIsLocked());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return YesOrNo.YES.getValue().equals(super.getUiIsEnabled());
  }
}
