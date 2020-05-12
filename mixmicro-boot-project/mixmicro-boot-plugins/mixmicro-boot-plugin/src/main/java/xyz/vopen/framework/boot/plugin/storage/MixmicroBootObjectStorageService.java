package xyz.vopen.framework.boot.plugin.storage;

import xyz.vopen.framework.boot.plugin.storage.exception.MixmicroBootObjectStorageException;
import xyz.vopen.framework.boot.plugin.storage.response.MixmicroBootObjectStorageResponse;

import java.io.InputStream;

/**
 * Mixmicro Boot Object Storage Interface Definition
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public interface MixmicroBootObjectStorageService {
  /**
   * byte array upload file
   *
   * @param objectName file name
   * @param bytes file byte array
   * @return {@link MixmicroBootObjectStorageResponse}
   * @throws MixmicroBootObjectStorageException object repository exception
   */
  MixmicroBootObjectStorageResponse upload(String objectName, byte[] bytes)
      throws MixmicroBootObjectStorageException;

  /**
   * input stream upload file
   *
   * @param objectName file name
   * @param inputStream file input stream
   * @return {@link MixmicroBootObjectStorageResponse}
   * @throws MixmicroBootObjectStorageException object repository exception
   */
  MixmicroBootObjectStorageResponse upload(String objectName, InputStream inputStream)
      throws MixmicroBootObjectStorageException;

  /**
   * local path upload file
   *
   * @param objectName file name
   * @param localFile file local path
   * @return {@link MixmicroBootObjectStorageResponse}
   * @throws MixmicroBootObjectStorageException object repository exception
   */
  MixmicroBootObjectStorageResponse upload(String objectName, String localFile)
      throws MixmicroBootObjectStorageException;

  /**
   * download file
   *
   * @param objectName file name in the object store
   * @param localFile file local path
   * @throws MixmicroBootObjectStorageException object repository exception
   */
  void download(String objectName, String localFile) throws MixmicroBootObjectStorageException;

  /**
   * delete file
   *
   * @param objectName file name in the object store
   * @throws MixmicroBootObjectStorageException object repository exception
   */
  void delete(String objectName) throws MixmicroBootObjectStorageException;
}
