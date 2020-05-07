package xyz.vopen.framework.boot.plugin.resource.load.enums;

import lombok.Getter;

/**
 * 资源存储事件
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-12 09:33
 */
@Getter
public enum ResourceStoreEvent {
  /** 查询资源 */
  SELECT,
  /** 添加资源 */
  INSERT,
  /** 更新资源 */
  UPDATE,
  /** 删除资源 */
  DELETE
}
