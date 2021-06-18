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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.sundeinfo.sypglass.relay.mapper.relay", sqlSessionFactoryRef = "relaySqlSessionFactory")
public class RelayServerDataConfig {

    @Bean(name = "relayDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.relay")
    public DataSource getRelayDateSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "relaySqlSessionFactory")
    @Primary
    public SqlSessionFactory relaySqlSessionFactory(@Qualifier("relayDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/relay/*.xml"));
        return bean.getObject();
    }

    @Bean("relaySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate relaySqlSessionTemplate(@Qualifier("relaySqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}