package com.jimisun.simplemybatis.config;

import com.jimisun.simplemybatis.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jimisun
 * @create 2022-03-26 2:20 PM
 **/
public class BoundSql {
    private String parseSql;

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String parseSql, List<ParameterMapping> parameterMappings) {
        this.parseSql = parseSql;
        this.parameterMappings = parameterMappings;
    }

    public String getParseSql() {
        return parseSql;
    }

    public void setParseSql(String parseSql) {
        this.parseSql = parseSql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    @Override
    public String toString() {
        return "BoundSql{" +
                "parseSql='" + parseSql + '\'' +
                ", parameterMappings=" + parameterMappings +
                '}';
    }
}
