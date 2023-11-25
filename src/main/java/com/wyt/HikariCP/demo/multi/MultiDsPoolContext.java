package com.wyt.HikariCP.demo.multi;

import org.springframework.stereotype.Component;

@Component
public class MultiDsPoolContext {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void setDataSourcePool(String dataSourcePool){
        threadLocal.set(dataSourcePool);
    }

    String getDataSourcePool(){
        return threadLocal.get();
    }
}
