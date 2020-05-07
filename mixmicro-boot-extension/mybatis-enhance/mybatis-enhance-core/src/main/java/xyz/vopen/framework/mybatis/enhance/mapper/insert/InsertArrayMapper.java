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
package xyz.vopen.framework.mybatis.enhance.mapper.insert;

import xyz.vopen.framework.mybatis.enhance.common.annotation.ProviderMapper;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import xyz.vopen.framework.mybatis.enhance.provider.insert.OrmInsertArrayProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/** @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a> */
@ProviderMapper
public interface InsertArrayMapper<T, Id extends Serializable> {
  /**
   * 添加多条数据
   *
   * @param array 数组形式添加
   * @throws EnhanceFrameworkException 增强框架异常
   */
  @InsertProvider(type = OrmInsertArrayProvider.class, method = "empty")
  void insertArray(@Param("array") T... array) throws EnhanceFrameworkException;
}
