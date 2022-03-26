package com.jimisun.simplemybatis.config;

import com.jimisun.simplemybatis.entity.Configuration;
import com.jimisun.simplemybatis.entity.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jimisun
 * @create 2022-03-26 1:03 PM
 **/
public class XmlMappedStatementBuilder {


    private Configuration configuration;

    public XmlMappedStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> allMappedStatement = new ArrayList<>();
        List<Element> selectElement = rootElement.selectNodes("//select");
        List<Element> updateElement = rootElement.selectNodes("//update");
        List<Element> deleteElement = rootElement.selectNodes("//delete");
        List<Element> insertElement = rootElement.selectNodes("//insert");
        allMappedStatement.addAll(selectElement);
        allMappedStatement.addAll(updateElement);
        allMappedStatement.addAll(deleteElement);
        allMappedStatement.addAll(insertElement);
        for (Element element : allMappedStatement) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramType = element.attributeValue("parameterType");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            String mappedStatementKey = namespace + "." + id;
            configuration.getMappedStatementMap().put(mappedStatementKey, mappedStatement);
        }
    }
}
