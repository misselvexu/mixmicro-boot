package xyz.vopen.framework.mybatis.enhance.dsl.where.sql.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件sql实体
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
@Data
public class ColumnWhereSQLEntity {
  /** 参数名称列表，数组为了in、not in、<>集合使用 */
  private List<String> paramNames = new ArrayList();
  /** 生成后的sql */
  private List<String> sqls = new ArrayList();
  /** 参数对应值列表，数组作用同paramNames集合 */
  private List<Object> values = new ArrayList();
}
