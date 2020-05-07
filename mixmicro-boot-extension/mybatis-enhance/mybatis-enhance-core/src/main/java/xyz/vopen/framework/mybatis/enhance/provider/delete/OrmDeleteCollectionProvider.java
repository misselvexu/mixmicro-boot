/**
 * The MIT License (MIT)
 *
 * <p>Copyright (c) 2018 VOPEN.XYZ
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xyz.vopen.framework.mybatis.enhance.provider.delete;

import xyz.vopen.framework.mybatis.enhance.common.ConfigConstants;
import xyz.vopen.framework.mybatis.enhance.common.helper.sql.MapperXmlMySqlHelper;
import xyz.vopen.framework.mybatis.enhance.common.struct.TableStruct;
import xyz.vopen.framework.mybatis.enhance.mapper.CrudMapper;
import xyz.vopen.framework.mybatis.enhance.provider.base.BaseProvider;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 批量删除Provider
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class OrmDeleteCollectionProvider extends BaseProvider {
  /**
   * 构造函数初始化全局变量
   *
   * @param mapperClass Mapper类型
   * @param entityClass 实体类型
   * @param method 方法实例
   */
  public OrmDeleteCollectionProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
    super(mapperClass, entityClass, method);
  }

  /**
   * 生成根据主键集合删除的sql语句 示例：userMapper.deleteCollection(new ArrayList(){ { add(7); add(8); add(9); }
   * });
   *
   * @param statement MappedStatement对象实例
   * @see CrudMapper#deleteCollection(Collection)
   */
  public void deleteCollection(MappedStatement statement) {
    // 获取数据表结构
    TableStruct tableStruct = getTableStruct();

    // 生成删除数据的sql
    String sql =
        MapperXmlMySqlHelper.script(
            MapperXmlMySqlHelper.delete(tableStruct.getTableName()),
            MapperXmlMySqlHelper.where(
                MapperXmlMySqlHelper.in(
                    tableStruct.getIdName(),
                    ConfigConstants.COLLECTION_PARAMETER_NAME,
                    ConfigConstants.PK_PARAMETER)));

    // 重新装载sqlSource
    reloadSqlSource(statement, sql);
  }
}
