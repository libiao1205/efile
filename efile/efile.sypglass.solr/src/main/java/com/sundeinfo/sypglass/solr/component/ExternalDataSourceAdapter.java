package com.sundeinfo.sypglass.solr.component;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;

public class ExternalDataSourceAdapter {

    private String driver;
    private String url;
    private String username;
    private String password;
    private String mapperPath;
    private String mapperPackage;

    public <T> T getMapper(Class<T> clazz) throws Exception {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.getMapper(clazz);
    }

    private SqlSessionFactory getSqlSessionFactory() throws Exception {
        PooledDataSource dataSource = createPooledDataSource();

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(mapperPath);

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(createConfiguration(dataSource));
        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean.getObject();

//        PooledDataSource dataSource = createPooledDataSource();
//        //使用 SqlSessionFactoryBuilder 构建 SqlSessionFactory
//        return new SqlSessionFactoryBuilder().build(createConfiguration(dataSource));
    }

    private PooledDataSource createPooledDataSource(){
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword (password);
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }

    private Configuration createConfiguration(PooledDataSource dataSource){
        // 采用 MyBatis 的 JDBC 事务方式
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment ("development", transactionFactory, dataSource);
        // 创建 Configuration 对象
        Configuration configuration = new Configuration(environment);
        // 注册一个 MyBatis 上下文别名
        configuration.getTypeAliasRegistry().registerAlias("Ex", ArrayList.class);
        // 加入一个映射器
        configuration.addMappers(mapperPackage);
        return configuration;
    }

//    public <T> Object getData(Class<T> clazz,String methodName, Object... args) throws Exception {
//        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        T mapper = sqlSession.getMapper(clazz);
//        Method method = mapper.getClass().getMethod(methodName);
//        Object result = method.invoke(mapper,args);
//        System.out.println(result);
//        return result;
//    }
//
//    public <T> Object getData(Class<T> clazz,String methodName, Object... args) throws Exception {
//        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        Class interfaceImpl = Class.forName(clazz.getName());
//        Object mapperInstance = Proxy.newProxyInstance(
//                interfaceImpl.getClassLoader(),
//                new Class[]{interfaceImpl},
//                new MapperInvocationHandler(sqlSession.getMapper(clazz))
//        );
//
//        Method method = mapperInstance.getClass().getMethod(methodName, Object.class);
//
//        Object result = method.invoke(mapperInstance,args);
//        System.out.println(result);
//        return result;
//    }
//
//    public class MapperInvocationHandler implements InvocationHandler {
//
//        private Object target;
//
//        MapperInvocationHandler(Object target) {
//            this.target = target;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            return method.invoke(target,args);
//        }
//    }

    public static class Builder {
        String driver;
        String url;
        String username;
        String password;
        String mapperPath;
        String mapperPackage;

        public void setDataSource(PooledDataSource dataSource) {
            this.driver = dataSource.getDriver();
            this.url = dataSource.getUrl();
            this.username = dataSource.getUsername();
            this.password = dataSource.getPassword();
        }

        public Builder setDriver(String driver) {
            this.driver = driver;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setMapperPath(String mapperPath) {
            this.mapperPath = mapperPath;
            return this;
        }

        public Builder setMapperPackage(String mapperPackage) {
            this.mapperPackage = mapperPackage;
            return this;
        }

        public ExternalDataSourceAdapter build(){
            ExternalDataSourceAdapter adapter = new ExternalDataSourceAdapter();
            adapter.driver = this.driver;
            adapter.url = this.url;
            adapter.username = this.username;
            adapter.password = this.password;
            adapter.mapperPath = this.mapperPath;
            adapter.mapperPackage = this.mapperPackage;
            return adapter;
        }

        public ExternalDataSourceAdapter build(PooledDataSource dataSource,String mapperPath,String mapperPackage){
            ExternalDataSourceAdapter adapter = new ExternalDataSourceAdapter();
            adapter.driver = dataSource.getDriver();
            adapter.url = dataSource.getUrl();
            adapter.username = dataSource.getUsername();
            adapter.password = dataSource.getPassword();
            adapter.mapperPath = mapperPath;
            adapter.mapperPackage = mapperPackage;
            return adapter;
        }
    }
}
