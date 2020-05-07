package xyz.vopen.framework.mybatis.pageable.common.enums;

import lombok.Getter;
import xyz.vopen.framework.mybatis.pageable.dialect.Dialect;
import xyz.vopen.framework.mybatis.pageable.dialect.support.*;

/**
 * 数据库方言枚举 该枚举用具DialectDynamicFactory工厂使用
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2020-04-11.
 */
@Getter
public enum DialectEnum {
  MYSQL(MySqlDialect.class),
  DB2(Db2Dialect.class),
  HSQL(HSqlDialect.class),
  ORACLE(OracleDialect.class),
  POSTGRES(PostgresDialect.class),
  SQLSERVER(SqlServerDialect.class),
  INfORMIX(InforMixDialect.class);

  DialectEnum(Class<? extends Dialect> value) {
    this.value = value;
  }

  private Class<? extends Dialect> value;
}
