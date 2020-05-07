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
package xyz.vopen.framework.mybatis.enhance.provider.select;

import xyz.vopen.framework.mybatis.enhance.common.ConfigConstants;
import xyz.vopen.framework.mybatis.enhance.common.helper.sql.MapperXmlMySqlHelper;
import xyz.vopen.framework.mybatis.enhance.common.mapping.ColumnMappingFilter;
import xyz.vopen.framework.mybatis.enhance.common.mapping.ColumnMappingFilterBuilder;
import xyz.vopen.framework.mybatis.enhance.common.mapping.MappingTypeEnum;
import xyz.vopen.framework.mybatis.enhance.common.struct.TableStruct;
import xyz.vopen.framework.mybatis.enhance.provider.base.BaseProvider;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * 查询集合Provider实现类
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
public class OrmSelectCollectionProvider extends BaseProvider {
  /**
   * 构造函数初始化全局变量
   *
   * @param mapperClass Mapper类型
   * @param entityClass 实体类型
   * @param method MappedStatement对应的方法实例
   */
  public OrmSelectCollectionProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
    super(mapperClass, entityClass, method);
  }

  /**
   * 根据编号集合查询对象集合
   *
   * @param statement MappedStatement对象实例
   */
  public void selectCollection(MappedStatement statement) {
    // 数据表结构对象
    TableStruct tableStruct = getTableStruct();
    // 构建列过滤对象
    ColumnMappingFilter mappingFilter =
        ColumnMappingFilterBuilder.builder()
            .mappingType(MappingTypeEnum.SELECT)
            .columnStructList(getColumnStruct())
            .build()
            .filter();

    // 处理in相关的查询条件
    String sql =
        MapperXmlMySqlHelper.script(
            MapperXmlMySqlHelper.select(tableStruct.getTableName(), mappingFilter.getColumnNames()),
            MapperXmlMySqlHelper.where(
                MapperXmlMySqlHelper.in(
                    tableStruct.getIdName(),
                    ConfigConstants.COLLECTION_PARAMETER_NAME,
                    ConfigConstants.PK_PARAMETER)));
    // 重新装载SqlSource
    reloadSqlSource(statement, sql);
    // 重新装载返回类型
    reloadEntityResultType(statement);
  }
}
