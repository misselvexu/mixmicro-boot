package xyz.vopen.framework.mybatis.enhance.dsl.factory;

import xyz.vopen.framework.mybatis.enhance.dsl.delete.Deleteable;
import xyz.vopen.framework.mybatis.enhance.dsl.delete.support.DeleteableSupport;
import xyz.vopen.framework.mybatis.enhance.dsl.factory.support.EnhanceDslSupport;
import xyz.vopen.framework.mybatis.enhance.dsl.serach.Searchable;
import xyz.vopen.framework.mybatis.enhance.dsl.serach.support.SearchableSupport;
import xyz.vopen.framework.mybatis.enhance.dsl.update.Updateable;
import xyz.vopen.framework.mybatis.enhance.dsl.update.support.UpdateableSupport;
import org.apache.ibatis.session.SqlSession;

/**
 * 该类为EnhanceDsl对象实例提供方法 构造函数需要传递继承SQLMapper的自定义Mapper，否则无法完成动态查询
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class EnhanceDslFactory {

  /** MyBatis内部对象SqlSession实例 通过该对象完成动态增、删、改、查 */
  private SqlSession sqlSession;

  public EnhanceDslFactory(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  /**
   * 获取一个新的SanmiDsl动态查询对象
   *
   * @return 动态接口实例
   */
  EnhanceDsl getDsl() {
    return new EnhanceDslSupport(sqlSession);
  }

  /**
   * 创建一个新的查询对象 该方法会初始化Searchable子类SearchableSupport实例
   * 而初始化SearchableSupport实例时需要将动态查询执行者SanmiDsl的实例SanmiDslImpl作为构造参数传递
   *
   * @return 查询对象实例
   */
  public Searchable createSearchable() {
    return new SearchableSupport(getDsl());
  }

  /**
   * 创建一个新的动态删除对象实例
   *
   * @return 删除对象实例
   */
  public Deleteable createDeleteable() {
    return new DeleteableSupport(getDsl());
  }

  /**
   * 创建一个新的动态更新对象实例
   *
   * @return 更新对象实例
   */
  public Updateable createUpdateable() {
    return new UpdateableSupport(getDsl());
  }
}
