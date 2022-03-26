package com.jimisun.simplemybatis.sqlSession;

import java.util.List;

/**
 * Sql会话
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId, Object... params);

    <E> E selectOne(String statementId, Object... params);

    int update(String statementId,Object... params);

    int insert(String statementId,Object... params);

    int delete(String statementId,Object... params);

    <T> T getMapper(Class<?> clazz);
}
