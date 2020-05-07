package xyz.vopen.framework.mybatis.enhance.dsl.where.sql.support;

import xyz.vopen.framework.mybatis.enhance.common.enums.PlaceholderEnum;
import xyz.vopen.framework.mybatis.enhance.dsl.where.sql.ColumnWhereSQL;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.springframework.util.StringUtils;

/**
 * 列查询条件工厂
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 */
public class ColumnWhereFactory {

  private ColumnWhereFactory() {}

  /**
   * 根据指定的占位符枚举值，获取对应的列条件实现
   *
   * @param placeholderEnum 占位符枚举值
   * @return 查询条件接口实现类
   */
  public static ColumnWhereSQL newInstance(PlaceholderEnum placeholderEnum, int paramIndex) {
    /** 返回接口实例 */
    ColumnWhereSQL columnWhereSQL = null;
    /** 根据枚举的值进行实例化查询条件实现类 */
    switch (placeholderEnum) {
      case EQ:
        columnWhereSQL = new EqColumnWhereSupport();
        break;
      case NEQ:
        columnWhereSQL = new NeColumnWhereSupport();
        break;
      case IN:
        columnWhereSQL = new InColumnWhereSupport();
        break;
      case LIKE:
        columnWhereSQL = new LikeColumnWhereSupport();
        break;
      case GT:
        columnWhereSQL = new GtColumnWhereSupport();
        break;
      case LT:
        columnWhereSQL = new LtColumnWhereSupport();
        break;
      case GET:
        columnWhereSQL = new GteColumnWhereSupport();
        break;
      case LET:
        columnWhereSQL = new LteColumnWhereSupport();
        break;
      case IS_NULL:
        columnWhereSQL = new IsNullColumnSupport();
        break;
      case IS_NOT_NULL:
        columnWhereSQL = new IsNotNullColumnWhereSupport();
        break;
      case IS_EMPTY:
        columnWhereSQL = new IsEmptyColumnSupport();
        break;
      case IS_NOT_EMPTY:
        columnWhereSQL = new IsNotEmptyColumnSupport();
        break;
    }
    if (StringUtils.isEmpty(columnWhereSQL)) {
      throw new EnhanceFrameworkException(
          "不支持的列查询条件：PlaceholderEnum > " + placeholderEnum.toString());
    }
    columnWhereSQL.setParamIndex(paramIndex);
    return columnWhereSQL;
  }
}
