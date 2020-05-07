package xyz.vopen.framework.mybatis.enhance.dsl.factory.support;

import xyz.vopen.framework.mybatis.enhance.common.helper.StatementHelper;
import xyz.vopen.framework.mybatis.enhance.dsl.delete.Deleteable;
import xyz.vopen.framework.mybatis.enhance.dsl.factory.EnhanceDsl;
import xyz.vopen.framework.mybatis.enhance.dsl.result.ReloadResultFactory;
import xyz.vopen.framework.mybatis.enhance.dsl.serach.Searchable;
import xyz.vopen.framework.mybatis.enhance.dsl.update.Updateable;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 注意：该类已被保护，仅SanmiDslFactory可以实例化 实现SanmiDsl内定义的动态查询方法【采用SQLMapper内selectBySQL方法实现】
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class EnhanceDslSupport implements EnhanceDsl {

  private SqlSession sqlSession;

  /**
   * 构造函数实例化SqlSession对象实例
   *
   * @param sqlSession
   */
  public EnhanceDslSupport(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  /**
   * 实现查询列表方法
   *
   * @param searchable 查询对象
   * @param <T> 结果类型
   * @return 查询对象结果列表
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public <T> List<T> select(Searchable searchable) throws EnhanceFrameworkException {
    // 创建动态查询的MappedStatement对象实例
    MappedStatement statement =
        StatementHelper.createOrGetDslStatement(
            sqlSession.getConfiguration(),
            searchable.getSql(),
            searchable.getParams(),
            SqlCommandType.SELECT);

    // 重新装载返回值类型
    // 基本数据类型 || 实体数据类型
    ReloadResultFactory.newInstance(searchable.getResultType()).reload(statement);

    // 通过SqlSession执行指定Id的MappedStatement
    return sqlSession.selectList(statement.getId(), searchable.getParams());
  }

  /**
   * 实现查询单个对象方法
   *
   * @param searchable 查询对象
   * @param <T> 结果类型
   * @return 查询结果实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public <T> T selectOne(Searchable searchable) throws EnhanceFrameworkException {

    // 创建动态查询的MappedStatement对象实例
    MappedStatement statement =
        StatementHelper.createOrGetDslStatement(
            sqlSession.getConfiguration(),
            searchable.getSql(),
            searchable.getParams(),
            SqlCommandType.SELECT);

    // 重新装载返回值类型
    // 基本数据类型 || 实体数据类型
    ReloadResultFactory.newInstance(searchable.getResultType()).reload(statement);

    // 通过SqlSession执行指定Id的MappedStatement
    return sqlSession.selectOne(statement.getId(), searchable.getParams());
  }

  /**
   * 实现动态删除方法
   *
   * @param deleteable 删除对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void delete(Deleteable deleteable) throws EnhanceFrameworkException {
    // 创建动态查询的MappedStatement对象实例
    MappedStatement statement =
        StatementHelper.createOrGetDslStatement(
            sqlSession.getConfiguration(),
            deleteable.getSql(),
            deleteable.getParams(),
            SqlCommandType.DELETE);
    // 执行删除操作
    sqlSession.delete(statement.getId(), deleteable.getParams());
  }

  /**
   * 实现动态更新方法
   *
   * @param updateable 更新对象实例
   * @throws EnhanceFrameworkException 框架异常
   */
  @Override
  public void update(Updateable updateable) throws EnhanceFrameworkException {
    // 创建动态查询的MappedStatement对象实例
    MappedStatement statement =
        StatementHelper.createOrGetDslStatement(
            sqlSession.getConfiguration(),
            updateable.getSql(),
            updateable.getParams(),
            SqlCommandType.UPDATE);
    // 执行更新操作
    sqlSession.update(statement.getId(), updateable.getParams());
  }
}
