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
package xyz.vopen.framework.mybatis.enhance.provider.named;

import xyz.vopen.framework.mybatis.enhance.common.helper.sql.MapperXmlMySqlHelper;
import xyz.vopen.framework.mybatis.enhance.common.struct.TableStruct;
import xyz.vopen.framework.mybatis.enhance.provider.base.BaseProvider;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * 方法规则查询Provider
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class MethodNamedCountProvider extends BaseProvider {
  /**
   * 实例化全局变量
   *
   * @param mapperClass Mapper类型
   * @param entityClass 实体类类型
   * @param method 方法对象
   */
  public MethodNamedCountProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
    super(mapperClass, entityClass, method);
  }

  /**
   * 方法规则查询方式的构建方法 所有匹配方法规则查询的方法都执行该方法重载SqlSource
   *
   * @param statement MappedStatement对象实例
   */
  public void build(MappedStatement statement) {
    // 获取表格结构信息
    TableStruct tableStruct = getTableStruct();

    // 构建sql
    String sql =
        MapperXmlMySqlHelper.script(
            MapperXmlMySqlHelper.count(tableStruct.getTableName(), tableStruct.getIdName()),
            MapperXmlMySqlHelper.where(
                MapperXmlMySqlHelper.named(getMethod().getName(), getColumnStructMap())));

    // 重新装载SqlSource
    reloadSqlSource(statement, sql);

    // 重新装载返回值类型
    reloadSingleResultType(statement, getMethod().getReturnType());
  }
}
