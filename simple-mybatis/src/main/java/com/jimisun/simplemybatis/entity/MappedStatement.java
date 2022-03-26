package com.jimisun.simplemybatis.entity;

/**
 * 映射配置类
 *
 * @author jimisun
 * @create 2022-03-26 10:38 AM
 **/
public class MappedStatement {

    private String id;

    private String resultType;

    private String paramterType;

    private String sql;

    public MappedStatement() {
    }

    public MappedStatement(String id, String resultType, String paramterType, String sql) {
        this.id = id;
        this.resultType = resultType;
        this.paramterType = paramterType;
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamterType() {
        return paramterType;
    }

    public void setParamterType(String paramterType) {
        this.paramterType = paramterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}

