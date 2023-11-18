package com.wyt.HikariCP.demo.multi;

import com.zaxxer.hikari.HikariConfig;
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

@PropertySource("classpath:mysql.properties")
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
//@ConditionalOnProperty(name = "dataSource", havingValue = "true")
@Setter
public class MultiDataSourceConfig {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String type;
    private String minConnection;
    private String MaxConnection;
    private String checkoutTimeoutMilliSec;
    private String jmxLevel;
    private String printSQL;
    private String infoSQLThreshold;
    private String sql;
    private String maximumPoolSize;
    private String connectionTimeout;


    @Bean
    @Primary
    public MultiDsPoolManager multiDsPoolManager(){
        MultiDsPoolManager manager = new MultiDsPoolManager();
        manager.setDefaultDataSource(dataSourceOne(hikariConfig()));
        return manager;
    }
    private DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
//        dataSource.setConnectionProperties(new Properties());
//        Properties properties = dataSource.getConnectionProperties();
//        properties.setProperty("driver-class-name",driver);
        return dataSource;
    }

    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName("com.zaxxer.hikari.HikariDataSource");
        hikariConfig.setDataSource(dataSource());
//        hikariConfig.addDataSourceProperty("userName",this.username);
//        hikariConfig.addDataSourceProperty("password",this.password);
//        hikariConfig.addDataSourceProperty("maxPoolSize",this.maximumPoolSize);
//        hikariConfig.addDataSourceProperty("password",this.password);
//        hikariConfig.addDataSourceProperty("password",this.password);
//        hikariConfig.addDataSourceProperty("password",this.password);
        return hikariConfig;
    }

    public HikariDataSource dataSourceOne(HikariConfig config) {
        return new HikariDataSource(config);
    }
}
