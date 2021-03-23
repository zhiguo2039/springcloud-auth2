package com.yzg.study.common.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.yzg.study.common.constants.GlobalConstant;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *  阿里巴巴连接池:DruidC配置
 */
@Configuration
public class DruidConfig {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("初始化连接池配置");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 白名单
        servletRegistrationBean.addInitParameter("allow", "");
        // 黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    /**
     * 解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
     * */
    @Component
    @ConfigurationProperties(prefix = GlobalConstant.DB_PREFIX)
    @Setter
    @Getter
    class IDataSourceProperties {
        private String url;
        private String name;
        private String username;
        private String password;
        private String driverClassName;
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String filters;
        private String connectionProperties;

        @Bean     //创建实例
        @Primary  //不写这个注解容易nullpoint
        public DataSource dataSource() {
            DruidDataSource datasource = new DruidDataSource();
            datasource.setUrl(url);
            datasource.setUsername(username);
            datasource.setPassword(password);
            datasource.setDriverClassName(driverClassName);
            //configuration
            datasource.setInitialSize(initialSize);
            datasource.setMinIdle(minIdle);
            datasource.setMaxActive(maxActive);
            datasource.setMaxWait(maxWait);
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            datasource.setValidationQuery(validationQuery);
            datasource.setTestWhileIdle(testWhileIdle);
            datasource.setTestOnBorrow(testOnBorrow);
            datasource.setTestOnReturn(testOnReturn);
            datasource.setPoolPreparedStatements(poolPreparedStatements);
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            try {
                datasource.setFilters(filters);
            } catch (SQLException e) {
                logger.error("druid configuration initialization filter: " + e);
            }
            datasource.setConnectionProperties(connectionProperties);
            logger.debug("druid configuration datasource 成功"  + datasource);
            logger.debug("druid configuration datasource 成功"  + name);
            return datasource;
        }
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();
        return dsInterceptor;
    }

    @Bean
    @Scope("prototype")
    /**
     * @Scope默认是单例模式，即scope="singleton"。
     *
     * 另外scope还有prototype、request、session、global session作用域。scope="prototype"多例
     *
     * 例:@scope默认是单例模式（singleton）即：@scope("prototype")
     * 1.singleton单例模式,
     *
     * 　　全局有且仅有一个实例
     *
     * 2.prototype原型模式，
     *
     * 　　每次获取Bean的时候会有一个新的实例
     *
     * 3.request　　
     *
     *    request表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效，
     *
     * 4.session　
     *
     *    session作用域表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
     *
     * 5.global session
     *
     *    global session作用域类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义。Portlet规范定义了全局Session的概念，它被所有构成某个 portlet web应用的各种不同的portlet所共享。在global session作用域中定义的bean被限定于全局portlet Session的生命周期范围内。如果你在web中使用global session作用域来标识bean，那么web会自动当成session类型来使用。
     *
     */
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("com.gaoxinqimeng.controller.*");
        pointcut.setPattern("com.gaoxinqimeng.mapper.*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }
}