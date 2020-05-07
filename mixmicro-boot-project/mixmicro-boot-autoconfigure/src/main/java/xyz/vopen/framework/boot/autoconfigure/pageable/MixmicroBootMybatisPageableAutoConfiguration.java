/*
 * Copyright (c) 2018 VOPEN.XYZ
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package xyz.vopen.framework.boot.autoconfigure.pageable;

import xyz.vopen.framework.mybatis.pageable.config.PageableConfigurer;
import xyz.vopen.framework.mybatis.pageable.interceptor.MyBatisExecutePageableInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Mixmicro Boot Mybatis Pageable Auto Configuration
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 *     <p>DateTime：2019-04-25 14:53
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(MixmicroBootMyBatisPageableProperties.class)
@ConditionalOnClass(MyBatisExecutePageableInterceptor.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MixmicroBootMybatisPageableAutoConfiguration {
  /** myabtis pageable properties */
  private MixmicroBootMyBatisPageableProperties myBatisPageableProperties;
  /** mybatis sqlSession factory */
  private List<SqlSessionFactory> sqlSessionFactoryList;
  /** mybatis pageable configurer */
  private PageableConfigurer pageableConfigurer;

  public MixmicroBootMybatisPageableAutoConfiguration(
      MixmicroBootMyBatisPageableProperties myBatisPageableProperties,
      ObjectProvider<List<SqlSessionFactory>> interceptorsProvider,
      ObjectProvider<PageableConfigurer> pageableConfigurerObjectProvider) {
    this.myBatisPageableProperties = myBatisPageableProperties;
    this.sqlSessionFactoryList = interceptorsProvider.getIfAvailable();
    this.pageableConfigurer = pageableConfigurerObjectProvider.getIfAvailable();
  }

  /** init interceptors */
  @PostConstruct
  void addInterceptors() {
    Interceptor interceptor = new MyBatisExecutePageableInterceptor();
    // set properties to interceptor
    interceptor.setProperties(myBatisPageableProperties.getProperties());

    for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
      // pre
      addPreInterceptors(sqlSessionFactory);
      // mybatis pageable interceptor
      sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
      // post
      addPostInterceptors(sqlSessionFactory);
    }
  }

  /**
   * pre interceptors
   *
   * @param sqlSessionFactory sqlSessionFactory对象实例
   */
  void addPreInterceptors(SqlSessionFactory sqlSessionFactory) {
    if (allowPageableConfigurer() && pageableConfigurer.prePlugins() != null) {
      loopAddInterceptor(pageableConfigurer.prePlugins(), sqlSessionFactory);
    }
  }

  /**
   * after interceptors
   *
   * @param sqlSessionFactory sqlSessionFactory对象实例
   */
  void addPostInterceptors(SqlSessionFactory sqlSessionFactory) {
    if (allowPageableConfigurer() && pageableConfigurer.postPlugins() != null) {
      loopAddInterceptor(pageableConfigurer.postPlugins(), sqlSessionFactory);
    }
  }

  /**
   * add interceptor to sqlSessionFactory
   *
   * @param interceptors interceptors
   * @param sqlSessionFactory sqlSessionFactory instance
   */
  void loopAddInterceptor(List<Interceptor> interceptors, SqlSessionFactory sqlSessionFactory) {
    for (Interceptor interceptor : interceptors) {
      sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }
  }

  /**
   * check have pageable configurer
   *
   * @return
   */
  boolean allowPageableConfigurer() {
    return pageableConfigurer != null;
  }
}
