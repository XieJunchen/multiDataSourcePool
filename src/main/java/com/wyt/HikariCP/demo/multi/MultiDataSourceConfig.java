package com.wyt.HikariCP.demo.multi;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class MultiDataSourceConfig {

    private Integer miniMumIdle = 10;
    private Integer maximumPoolSize = 10;
    private Integer idleTimeout = 500000;
    private Integer connectionTimeout = 60000;
    private Integer maxLifetime = 540000;

    @Bean("multiDsPoolManager")
    public MultiDsPoolManager multiDsPoolManager(){
        MultiDsPoolManager manager = new MultiDsPoolManager();
        manager.setDefaultDataSource(createHikariDataSource(dataSourceProperties()));
        return manager;
    }
    /**
     * 创建数据源的配置对象
     */
    @Primary
    @Bean(name = "dataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource") // 读取 spring.datasource 配置到 DataSourceProperties 对象
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    private HikariDataSource createHikariDataSource(DataSourceProperties properties) {
        // 创建 HikariDataSource 对象
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        // 设置线程池名
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        dataSource.setMinimumIdle(this.miniMumIdle);
        dataSource.setMaximumPoolSize(this.maximumPoolSize);
        dataSource.setIdleTimeout(this.idleTimeout);
        dataSource.setConnectionTimeout(this.connectionTimeout);
        dataSource.setMaxLifetime(this.maxLifetime);
        return dataSource;
    }
}

