package pers.walyex.mybatis.factory;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.mybatis.configuration.MyConfiguration;
import pers.walyex.mybatis.configuration.MyMapperStatement;
import pers.walyex.mybatis.sqlsession.MyDefaultSqlSession;
import pers.walyex.mybatis.sqlsession.MySqlSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 1、初始化时候就完成configuration的实例化
 * 2、工厂类，生成sqlSession
 *
 * @author Waldron Ye
 * @date 2021/3/14 11:08
 */
@Slf4j
public class MyDefaultSqlSessionFactory implements MySqlSessionFactory {

    private MyConfiguration myConfiguration = new MyConfiguration();

    /**
     * mapper文件路径
     */
    private static final String MAPPER_PATH = "mapper";

    /**
     * 数据库信息路径
     */
    private static final String JDBC_PATH = "jdbc.properties";


    public MyDefaultSqlSessionFactory() {
        try {
            //1、加载数据库信息
            this.loadDbInfo();
            //2、加载MapperInfo
            this.loadMapperInfo();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private void loadDbInfo() throws IOException {
        //获取InputStream
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(JDBC_PATH);
        //把InputStream加载到Properties
        Properties properties = new Properties();
        properties.load(inputStream);
        //把配置信息set到Configuration
        this.myConfiguration.setJdbcDriver(properties.getProperty("jdbc.driver"));
        this.myConfiguration.setJdbcPassword(properties.getProperty("jdbc.password"));
        this.myConfiguration.setJdbcUrl(properties.getProperty("jdbc.url"));
        this.myConfiguration.setJdbcUsername(properties.getProperty("jdbc.username"));
        log.info("加载数据库信息:{}", this.myConfiguration);
    }

    private void loadMapperInfo() {
        //获取URL
        URL url = this.getClass().getClassLoader().getResource(MAPPER_PATH);

        //获取File
        File file = new File(url.getFile());
        //如果是目录
        if (file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(t -> this.loadMapperInfo(t));
        }
        log.info("加载myMapperStatementMap:{}", FastJsonUtil.toJson(this.myConfiguration.getMyMapperStatementMap()));
    }


    private void loadMapperInfo(File file) {
        SAXReader reader = new SAXReader();
        try {
            Document read = reader.read(file);
            Element rootElement = read.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            List<Element> selects = rootElement.elements("select");
            List<Element> inserts = rootElement.elements("insert");
            List<Element> updates = rootElement.elements("update");
            List<Element> deletes = rootElement.elements("delete");

            List<Element> list = new ArrayList<>();
            list.addAll(selects);
            list.addAll(inserts);
            list.addAll(updates);
            list.addAll(deletes);

            MyMapperStatement myMapperStatement;
            for (Element element : list) {
                myMapperStatement = new MyMapperStatement();
                myMapperStatement.setId(namespace + "," + element.attribute("id").getData().toString());
                myMapperStatement.setNamespace(namespace);
                myMapperStatement.setSql(element.getText());
                myMapperStatement.setResultType(element.attribute("parameterType").getData().toString());
                this.myConfiguration.getMyMapperStatementMap().put(myMapperStatement.getId(), myMapperStatement);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public MySqlSession openSession() {
        return new MyDefaultSqlSession(this.myConfiguration);
    }

}
