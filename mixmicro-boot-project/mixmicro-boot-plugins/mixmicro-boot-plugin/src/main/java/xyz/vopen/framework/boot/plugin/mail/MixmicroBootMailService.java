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

package xyz.vopen.framework.boot.plugin.mail;

import xyz.vopen.framework.boot.common.exception.MixmicroBootException;
import xyz.vopen.framework.boot.plugin.mail.request.MixmicroBootMailRequest;
import xyz.vopen.framework.boot.plugin.mail.response.MixmicroBootMailResponse;

/**
 * Mixmicro Boot Mail Service Interface
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootMailService {
  /**
   * send mail
   *
   * @param mixmicroBootMailRequest {@link MixmicroBootMailRequest}
   * @return {@link MixmicroBootMailResponse}
   * @throws MixmicroBootException Mixmicro Boot Exception
   */
  MixmicroBootMailResponse sendMail(MixmicroBootMailRequest mixmicroBootMailRequest) throws MixmicroBootException;
}
