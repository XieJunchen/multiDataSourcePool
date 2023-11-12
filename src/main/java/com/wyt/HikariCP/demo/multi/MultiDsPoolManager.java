package com.wyt.HikariCP.demo.multi;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.metrics.MetricsTrackerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.ConcurrentHashMap;

public class MultiDsPoolManager implements AbstractMultiPoolManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDsPoolManager.class);
    /**
     * 默认连接池
     */
    private DataSource defaultDataSource;
    /**
     * 数据源配置
     */
    private MultiDataSourceConfig dataSourceConfig;
    /**
     * 连接池集合
     */
    private final ConcurrentHashMap<String, DataSource> pools = new ConcurrentHashMap<>(8);

    public MultiDsPoolManager() {
        this.defaultDataSource = null;
    }
    public void setDefaultDataSource(DataSource dataSource) {
        this.defaultDataSource = dataSource;
    }

    public DataSource getDataSource() {
        DataSource dataSource = null;
        synchronized (pools) {
            if (pools.size() == 0) {
                // todo
                dataSource = new HikariDataSource();
                pools.put("master",dataSource);
            }
        }
        dataSource = pools.get("master");
        return dataSource;
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
