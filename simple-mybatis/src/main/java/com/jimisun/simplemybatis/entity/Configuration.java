package com.jimisun.simplemybatis.entity;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * simple-mybatis核心配置类
 *
 * @author jimisun
 * @create 2022-03-26 10:37 AM
 **/
public class Configuration {

    private DataSource dataSource;
    
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    public Configuration() {
    }

    public Configuration(DataSource dataSource, Map<String, MappedStatement> mappedStatementMap) {
        this.dataSource = dataSource;
        this.mappedStatementMap = mappedStatementMap;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
