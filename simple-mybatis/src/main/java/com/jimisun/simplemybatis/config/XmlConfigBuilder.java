package com.jimisun.simplemybatis.config;

import com.jimisun.simplemybatis.entity.Configuration;
import com.jimisun.simplemybatis.io.Resources;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author jimisun 将字节输入流加载为Configuration对象
 * @create 2022-03-26 10:53 AM
 **/
public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        // step 2.1 将sqlMapperConfig中的数据库配置信息加载为链接池对象
        Element rootElement = document.getRootElement();
        List<Element> propertyList = rootElement.selectNodes("//property");
        Properties dataSourceProperties = new Properties();
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            dataSourceProperties.setProperty(name, value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(String.valueOf(dataSourceProperties.get("driverClass")));
        comboPooledDataSource.setJdbcUrl(String.valueOf(dataSourceProperties.get("jdbcUrl")));
        comboPooledDataSource.setUser(String.valueOf(dataSourceProperties.get("username")));
        comboPooledDataSource.setPassword(String.valueOf(dataSourceProperties.get("password")));
        configuration.setDataSource(comboPooledDataSource);
        //step 2.2 将sqlMapperConfig中的mapper信息加载为MappedStatement对象
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String resource = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            XmlMappedStatementBuilder xmlMappedStatementBuild = new XmlMappedStatementBuilder(configuration);
            xmlMappedStatementBuild.parse(resourceAsStream);
        }
        return configuration;
    }
}
