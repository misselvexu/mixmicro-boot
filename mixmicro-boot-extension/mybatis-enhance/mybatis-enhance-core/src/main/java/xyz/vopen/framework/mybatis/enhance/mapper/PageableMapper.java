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
package xyz.vopen.framework.mybatis.enhance.mapper;

import xyz.vopen.framework.mybatis.enhance.common.annotation.ProviderMapper;
import xyz.vopen.framework.mybatis.enhance.exception.EnhanceFrameworkException;
import xyz.vopen.framework.mybatis.enhance.page.Pageable;
import xyz.vopen.framework.mybatis.enhance.provider.select.OrmSelectPageableProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version}
 */
@ProviderMapper
public interface PageableMapper<T, Id extends Serializable> {

  /**
   * 分页查询
   *
   * @param pageable 分页对象
   * @return T类型的集合
   * @throws EnhanceFrameworkException 增强框架异常
   */
  @SelectProvider(type = OrmSelectPageableProvider.class, method = "empty")
  List<T> selectByPageable(@Param("pageable") Pageable pageable) throws EnhanceFrameworkException;
}
