package com.sundeinfo.sypglass.relay.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.sundeinfo.sypglass.relay.mapper.oa", sqlSessionFactoryRef = "oaSqlSessionFactory")
public class OaServerDataConfig {

    @Bean(name = "oaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.oa")
    public DataSource getOaDateSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oaSqlSessionFactory")
    public SqlSessionFactory oaSqlSessionFactory(@Qualifier("oaDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/oa/*.xml"));
        return bean.getObject();
    }

    @Bean("oaSqlSessionTemplate")
    public SqlSessionTemplate oaSqlSessionTemplate(@Qualifier("oaSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}