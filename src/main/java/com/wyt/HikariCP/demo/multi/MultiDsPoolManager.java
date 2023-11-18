package com.wyt.HikariCP.demo.multi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import org.apache.ibatis.util.MapUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MultiDsPoolManager implements AbstractMultiPoolManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDsPoolManager.class);
    /**
     * 默认连接池
     */
    private HikariDataSource defaultDataSource;
    /**
     * 数据源配置
     */
    private MultiDataSourceConfig multiDataSourceConfig;
    /**
     * 连接池集合
     */
    private final ConcurrentHashMap<String, DataSource> pools = new ConcurrentHashMap<>(8);

    public MultiDsPoolManager() {
        this.defaultDataSource = null;
    }
    public void setDefaultDataSource(HikariDataSource dataSource) {
        this.defaultDataSource = dataSource;
    }

    public DataSource getDataSource() {
        if (pools.isEmpty()){
            synchronized (pools) {
                if (pools.isEmpty()){
                    LOGGER.info("当前连接池为空，开始初始化连接池。");
                    HikariDataSource source = new HikariDataSource(copyConfigFromDefault());
                    pools.put("master",source);
                }
            }
        }
        DataSource dataSource = pools.get("master");
        return Objects.isNull(dataSource) ? defaultDataSource : dataSource ;
    }

    @NotNull
    private HikariConfig copyConfigFromDefault() {
        HikariConfig config = new HikariConfig();
        config.setAutoCommit(defaultDataSource.isAutoCommit());
        config.setCatalog(defaultDataSource.getCatalog());
        config.setConnectionInitSql(defaultDataSource.getConnectionInitSql());
        config.setConnectionTimeout(defaultDataSource.getConnectionTimeout());
        config.setDataSource(defaultDataSource.getDataSource());
//        config.setDriverClassName(defaultDataSource.getDriverClassName());
        config.setDataSourceClassName(defaultDataSource.getDataSourceClassName());
        config.setDataSourceJNDI(defaultDataSource.getDataSourceJNDI());
        config.setDataSourceProperties(defaultDataSource.getDataSourceProperties());
        config.setAllowPoolSuspension(defaultDataSource.isAllowPoolSuspension());
//        config.setExceptionOverrideClassName(defaultDataSource.getExceptionOverrideClassName());
        config.setHealthCheckRegistry(defaultDataSource.getHealthCheckRegistry());
        config.setHealthCheckProperties(defaultDataSource.getHealthCheckProperties());
        config.setIdleTimeout(defaultDataSource.getIdleTimeout());
        config.setInitializationFailTimeout(defaultDataSource.getInitializationFailTimeout());
        config.setIsolateInternalQueries(defaultDataSource.isIsolateInternalQueries());
        config.setJdbcUrl(defaultDataSource.getJdbcUrl());
        config.setKeepaliveTime(defaultDataSource.getKeepaliveTime());
        config.setLeakDetectionThreshold(defaultDataSource.getLeakDetectionThreshold());
        config.setMaximumPoolSize(defaultDataSource.getMaximumPoolSize());
        config.setMetricRegistry(defaultDataSource.getMetricRegistry());
        config.setMetricsTrackerFactory(defaultDataSource.getMetricsTrackerFactory());
        config.setMaxLifetime(defaultDataSource.getMaxLifetime());
        config.setMinimumIdle(defaultDataSource.getMinimumIdle());
        config.setPassword(defaultDataSource.getPassword());
        config.setSchema(defaultDataSource.getSchema());
        config.setScheduledExecutor(defaultDataSource.getScheduledExecutor());
        config.setThreadFactory(defaultDataSource.getThreadFactory());
        config.setTransactionIsolation(defaultDataSource.getTransactionIsolation());
        config.setUsername(defaultDataSource.getUsername());
        config.setValidationTimeout(defaultDataSource.getValidationTimeout());
        return config;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDataSource().getConnection(username, password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.getLoginTimeout();
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.getParentLogger();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.isWrapperFor(iface);
    }

    public void setMetricRegistry(Object metricRegistry) {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        dataSource.setMetricRegistry(metricRegistry);
    }

    public void setMetricsTrackerFactory(MetricsTrackerFactory metricsTrackerFactory) {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        dataSource.setMetricsTrackerFactory(metricsTrackerFactory);
    }

    public void setHealthCheckRegistry(Object healthCheckRegistry) {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        dataSource.setHealthCheckRegistry(healthCheckRegistry);
    }

    public boolean isRunning() {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.isRunning();
    }

    public HikariPoolMXBean getHikariPoolMXBean() {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.getHikariPoolMXBean();
    }

    public HikariConfigMXBean getHikariConfigMXBean() {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.getHikariConfigMXBean();
    }

    public void evictConnection(Connection connection) {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        dataSource.evictConnection(connection);
    }

    public void close() {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        dataSource.close();
    }

    public boolean isClosed() {
        HikariDataSource dataSource = (HikariDataSource) getDataSource();
        return dataSource.isClosed();
    }

    public String toString() {
        return "DataSourcePoolManager";
    }
}
