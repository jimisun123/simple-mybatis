package com.jimisun.simplemybatis.sqlSession;

import com.jimisun.simplemybatis.entity.Configuration;
import com.jimisun.simplemybatis.entity.MappedStatement;
import com.jimisun.simplemybatis.executor.Executor;
import com.jimisun.simplemybatis.executor.SimpleExecutor;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 默认的Sql回话
 * 封装来一些方法进行操作
 *
 * @author jimisun
 * @create 2022-03-26 1:26 PM
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //此处委托给executor执行
        Executor executor = new SimpleExecutor();
        try {
            return executor.query(configuration, mappedStatement, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> E selectOne(String statementId, Object... params) {
        List<Object> objects = selectList(statementId, params);
        if (objects == null) {
            return null;
        } else if (objects.size() == 1) {
            return (E) objects.get(0);
        } else {
            throw new RuntimeException("返回内容存在多个...");
        }
    }

    @Override
    public int update(String statementId, Object... params) {
        Executor executor = new SimpleExecutor();
        try {
            return executor.update(configuration, configuration.getMappedStatementMap().get(statementId), params);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insert(String statementId, Object... params) {
        return update(statementId, params);
    }

    @Override
    public int delete(String statementId, Object... params) {
        return update(statementId, params);
    }

    @Override
    public <T> T getMapper(Class<?> clazz) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //调用selectOne或者selectList
                String name = method.getName();
                String clazzName = method.getDeclaringClass().getName();
                String mappedStatementId = clazzName + "." + name;
                //根据sql判断应该调用哪种方法执行sql
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(mappedStatementId);
                String sql = mappedStatement.getSql();
                if (sql.toLowerCase().startsWith("select")) {
                    //根据返回值是否进行泛型化选择调用selectList和selectOne
                    Type genericReturnType = method.getGenericReturnType();
                    if (genericReturnType instanceof ParameterizedType) {
                        return selectList(mappedStatementId, args);
                    } else {
                        return selectOne(mappedStatementId, args);
                    }
                } else if (sql.toLowerCase().startsWith("update")) {
                    return update(mappedStatementId, args);
                } else if (sql.toLowerCase().startsWith("delete")) {
                    return delete(mappedStatementId, args);
                } else if (sql.toLowerCase().startsWith("insert")) {
                    return insert(mappedStatementId, args);
                } else {
                    //TODO 其他默认全部走更新方法 该功能需要进行详细测试
                    return update(mappedStatementId, args);
                }

            }
        });
        return (T) proxyInstance;
    }
}
