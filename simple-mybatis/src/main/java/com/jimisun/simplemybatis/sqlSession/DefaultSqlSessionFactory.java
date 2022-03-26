package com.jimisun.simplemybatis.sqlSession;

import com.jimisun.simplemybatis.entity.Configuration;

/**
 * 默认的SqlSessionFactory实现
 *
 * @author jimisun
 * @create 2022-03-26 1:22 PM
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSqlSession() {
       return new DefaultSqlSession(configuration);
    }
}
