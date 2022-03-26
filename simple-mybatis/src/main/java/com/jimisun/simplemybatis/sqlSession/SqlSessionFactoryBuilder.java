package com.jimisun.simplemybatis.sqlSession;

import com.jimisun.simplemybatis.config.XmlConfigBuilder;
import com.jimisun.simplemybatis.entity.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * sqlSession工程的builder
 *
 * @author jimisun
 * @create 2022-03-26 10:50 AM
 **/
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream inputStream) {
        //step 2 ： 将字节输入流加载为具体的容器对象【configuration,mappedStatement】
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = new Configuration();
        try {
            configuration = xmlConfigBuilder.parseConfig(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        //step 3 创建sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
