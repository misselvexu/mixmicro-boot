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

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;

import java.math.BigDecimal;

/**
 * Mixmicro Boot Oss Progress Listener
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class OssProgressListener implements ProgressListener {
  /** already write bytes */
  private long bytesWritten = 0;
  /** file total bytes */
  private long totalBytes = -1;
  /** percent scale */
  private int percentScale = 2;
  /** oss object name */
  private String objectName;
  /** Mixmicro Boot Progress */
  private MixmicroBootObjectStorageProgress mixmicroBootObjectStorageProgress;

  public OssProgressListener(
      String objectName, MixmicroBootObjectStorageProgress mixmicroBootObjectStorageProgress) {
    this.objectName = objectName;
    this.mixmicroBootObjectStorageProgress = mixmicroBootObjectStorageProgress;
  }

  @Override
  public void progressChanged(ProgressEvent progressEvent) {
    if (mixmicroBootObjectStorageProgress != null) {
      // current progress bytes
      long bytes = progressEvent.getBytes();
      // progress event type
      ProgressEventType eventType = progressEvent.getEventType();

      switch (eventType) {
          // sent file total bytes
        case REQUEST_CONTENT_LENGTH_EVENT:
          this.totalBytes = bytes;
          break;
          // request byte transfer
        case REQUEST_BYTE_TRANSFER_EVENT:
          this.bytesWritten += bytes;
          if (this.totalBytes != -1) {
            // Calculation percent
            double percent = (this.bytesWritten * 100.00 / this.totalBytes);
            BigDecimal decimal =
                BigDecimal.valueOf(percent).setScale(percentScale, BigDecimal.ROUND_DOWN);
            mixmicroBootObjectStorageProgress.progress(
                objectName, decimal.doubleValue(), this.totalBytes, this.bytesWritten);
          }
          break;
          // complete
        case TRANSFER_COMPLETED_EVENT:
          mixmicroBootObjectStorageProgress.success(objectName);
          break;
          // failed
        case TRANSFER_FAILED_EVENT:
          mixmicroBootObjectStorageProgress.failed(objectName);
          break;
        default:
          break;
      }
    }
  }
}
