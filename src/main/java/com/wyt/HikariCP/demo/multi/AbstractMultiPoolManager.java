package com.wyt.HikariCP.demo.multi;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public interface AbstractMultiPoolManager extends DataSource {
    @Override
     default PrintWriter getLogWriter() throws SQLException{
        throw new UnsupportedOperationException("getLogWriter");
    };

    @Override
    default void setLogWriter(PrintWriter out) throws SQLException{
        throw new UnsupportedOperationException("setLogWriter");
    }

    @Override
    default void setLoginTimeout(int seconds) throws SQLException{
        throw new UnsupportedOperationException("setLoginTimeout");
    }

    @Override
    default int getLoginTimeout() throws SQLException{
        return 0;
    }

    @Override
    default  <T> T unwrap(Class<T> iface) throws SQLException{
        if (iface.isInstance(this)) {
            return (T) this;
        } else {
            throw new SQLException("DataSource of type [" + this.getClass().getName() + "] cannot be unwrapped as [" + iface.getName() + "]");
        }
    }

    @Override
    default boolean isWrapperFor(Class<?> iface) throws SQLException{
        return iface.isInstance(this);
    }

    @Override
    default Logger getParentLogger() throws SQLFeatureNotSupportedException{
        return Logger.getLogger("AbstractMultiPoolManager");
    }
}
