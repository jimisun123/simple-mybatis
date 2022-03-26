package com.jimisun.simplemybatis.executor;

import com.jimisun.simplemybatis.entity.Configuration;
import com.jimisun.simplemybatis.entity.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 执行器 ： 具体执行sql的执行器
 */
public interface Executor {
    /**
     * 此方法为最终的执行jdbc sql的方法 【查询】
     *
     * @param configuration   Simple-Mybatis核心配置容器对象
     * @param mappedStatement Simple-Mybatis Mapped
     * @param params          参数
     * @param <E>             返回值
     * @return
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException, IntrospectionException, InvocationTargetException;

    /**
     * 此方法为最终的执行jdbc sql的方法 【更新】
     *
     * @param configuration
     * @param mappedStatement
     * @param params
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException;

}
