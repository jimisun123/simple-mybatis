package com.jimisun.simplemybatis.executor;

import com.jimisun.simplemybatis.config.BoundSql;
import com.jimisun.simplemybatis.entity.Configuration;
import com.jimisun.simplemybatis.entity.MappedStatement;
import com.jimisun.simplemybatis.utils.GenericTokenParser;
import com.jimisun.simplemybatis.utils.ParameterMapping;
import com.jimisun.simplemybatis.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单的Sql执行器
 *
 * @author jimisun
 * @create 2022-03-26 1:56 PM
 **/
public class SimpleExecutor implements Executor {


    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException, InstantiationException, IntrospectionException, InvocationTargetException {
        //获取可执行的 PreparedStatement
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);

        //step 4.4 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //step 4.5 封装结果集
        List<Object> result = new ArrayList<>();
        String resultType = mappedStatement.getResultType();
        Class<?> classResultType = getClassType(resultType);
        while (resultSet.next()) {
            Object o = classResultType.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object value = resultSet.getObject(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, classResultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            result.add(o);
        }
        return (List<E>) result;
    }


    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, SQLException {
        //获取可执行的 PreparedStatement
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);
        return preparedStatement.executeUpdate();
    }


    /**
     * 获取封装好的PreparedStatement
     *
     * @param configuration
     * @param mappedStatement
     * @param params
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //step 4.1 获取jdbc链接
        Connection connection = null;
        try {
            connection = configuration.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //step 4.2 获取要执行的sql 这里需要解析sql
        String sql = mappedStatement.getSql();
        BoundSql boundSql = boundSql(sql);

        //step 4.3 给jdbc链接设置参数
        PreparedStatement preparedStatement =
                preparedStatement = connection.prepareStatement(boundSql.getParseSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String parameterType = mappedStatement.getParamterType();
        Class<?> paramTypeClass = paramTypeClass = getClassType(parameterType);
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String name = parameterMapping.getName();
            Field declaredField = paramTypeClass.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
        return preparedStatement;
    }

    /**
     * 根据类权限定名获取类类型
     *
     * @param paramterType
     * @return
     * @throws ClassNotFoundException
     */
    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            return Class.forName(paramterType);
        }
        throw new ClassNotFoundException();
    }

    /**
     * 解析sql
     * 1. 将sql中的#{} 替换为 ?
     * 2. 将sql中的#{} 中的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql boundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);

    }

}
