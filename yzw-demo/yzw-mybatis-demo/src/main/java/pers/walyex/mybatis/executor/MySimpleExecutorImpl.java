package pers.walyex.mybatis.executor;

import lombok.extern.slf4j.Slf4j;
import pers.walyex.mybatis.configuration.MyConfiguration;
import pers.walyex.mybatis.configuration.MyMapperStatement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MySimpleExecutorImpl implements MyExecutor {

    private MyConfiguration myConfiguration;

    public MySimpleExecutorImpl(MyConfiguration myConfiguration) {
        this.myConfiguration = myConfiguration;
    }

    @Override
    public <T> List<T> query(MyMapperStatement myMapperStatement, Object parameter) {
        //1.加载驱动程序
        try {
            Class.forName(this.myConfiguration.getJdbcDriver());

            //2. 获得数据库连接
            Connection conn = DriverManager.getConnection(this.myConfiguration.getJdbcUrl(), this.myConfiguration.getJdbcUsername(), this.myConfiguration.getJdbcPassword());
            //3.操作数据库，实现增删改查
            Statement statement = conn.createStatement();
            String sql = myMapperStatement.getSql();
            log.info("执行的sql:{}", sql);
            //todo 暂时写死sql
            sql = "SELECT * FROM sys_user";
            ResultSet resultSet = statement.executeQuery(sql);

            List<T> list = new ArrayList<>();
            //todo 反射处理
            handleResult(resultSet, list, myMapperStatement.getResultType());
            conn.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <E> void handleResult(ResultSet resultSet, List<E> list, String resultType) {

        Class<E> eClass = null;
        try {
            eClass = (Class<E>) Class.forName(resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!resultSet.next()) {
                    break;
                }
                Object object = eClass.getDeclaredConstructor().newInstance();
                //todo 反射赋值
                list.add((E) object);
            } catch (SQLException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
