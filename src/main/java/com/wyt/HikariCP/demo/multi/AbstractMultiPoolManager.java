package com.wyt.HikariCP.demo.multi;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public interface AbstractMultiPoolManager extends DataSource {
    @Override
     default PrintWriter getLogWriter() throws SQLException{
        return null;
    };

    @Override
    default void setLogWriter(PrintWriter out) throws SQLException{
    }

    @Override
    default void setLoginTimeout(int seconds) throws SQLException{
    }

    @Override
    default int getLoginTimeout() throws SQLException{
        return 0;
    }

    @Override
    default  <T> T unwrap(Class<T> iface) throws SQLException{
        return null;
    }

    @Override
    default boolean isWrapperFor(Class<?> iface) throws SQLException{
        return false;
    }

    @Override
    default Logger getParentLogger() throws SQLFeatureNotSupportedException{
        return null;
    }
}
