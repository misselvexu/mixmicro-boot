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

package xyz.vopen.framework.boot.plugin.oss.progress;

import xyz.vopen.framework.boot.plugin.storage.exception.MixmicroBootObjectStorageException;

/**
 * Mixmicro Boot Oss Upload and Download Progress
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootObjectStorageProgress {
  /**
   * progress
   *
   * @param objectName object name
   * @param percent upload or download progress percent
   * @param totalBytes total bytes
   * @param currentWrittenBytes already written bytes
   * @throws MixmicroBootObjectStorageException Mixmicro Boot Oss Exception
   */
  void progress(String objectName, double percent, long totalBytes, long currentWrittenBytes)
      throws MixmicroBootObjectStorageException;

  /**
   * upload or download success
   *
   * @param objectName object name
   * @throws MixmicroBootObjectStorageException Mixmicro Boot Oss Exception
   */
  void success(String objectName) throws MixmicroBootObjectStorageException;

  /**
   * upload or download failed
   *
   * @param objectName object name
   * @throws MixmicroBootObjectStorageException Mixmicro Boot Oss Exception
   */
  void failed(String objectName) throws MixmicroBootObjectStorageException;
}
