package com.wyt.HikariCP.demo.multi;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Properties;

@PropertySource("classpath:")
@Configuration
//@ConfigurationProperties("dataSource")
//@ConditionalOnProperty(name = "dataSource", havingValue = "true")
@Setter
public class MultiDataSourceConfig {
    private String driver;
    private String url;
    private String route2all;
    private String className;
    private String minConnection;
    private String MaxConnection;
    private String checkoutTimeoutMilliSec;
    private String jmxLevel;
    private String printSQL;
    private String infoSQLThreshold;
    private String sql;
    private String maximumPoolSize;
    private String connectionTimeout;
    private DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driver);
        dataSource.setUrl(url);
        dataSource.setConnectionProperties(new Properties());
        Properties properties = dataSource.getConnectionProperties();
        properties.setProperty("driver-class-name",className);
        properties.setProperty("driver-class-name",className);
        properties.setProperty("driver-class-name",className);
        properties.setProperty("driver-class-name",className);
        properties.setProperty("driver-class-name",className);
        return dataSource;
    }
    /**
     * 创建 HikariDataSource 数据源
     *
     * @param properties 参数
     * @return 数据源
     */
    private static HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = createHikariDataSource(properties, HikariDataSource.class);
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

    @Bean
    @Primary
    public MultiDsPoolManager multiDsPoolManager(){
        MultiDsPoolManager manager = new MultiDsPoolManager();
        manager.setDefaultDataSource(dataSourceOne(dataSourcePropertiesOne()));
        return manager;
    }
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourcePropertiesOne() {
        return new DataSourceProperties();
    }

    @ConfigurationProperties("spring.datasource")
    public HikariDataSource dataSourceOne(DataSourceProperties properties) {
        return createHikariDataSource(properties);
    }

//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties dataSourcePropertiesTwo() {
//        return new DataSourceProperties();
//    }

//    @Bean
//    @ConfigurationProperties("spring.datasource.two.hikari")
//    public HikariDataSource dataSourceTwo(@Qualifier("dataSourcePropertiesTwo") DataSourceProperties properties) {
//        return createHikariDataSource(properties);
//    }


    @SuppressWarnings("unchecked")
    private static <T> T createHikariDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
        return (T) properties.initializeDataSourceBuilder().type(type).build();
    }
}
