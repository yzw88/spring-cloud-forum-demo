package pers.walyex.mybatis.sqlsession;

import pers.walyex.mybatis.configuration.MyConfiguration;
import pers.walyex.mybatis.executor.MyExecutor;
import pers.walyex.mybatis.executor.MySimpleExecutorImpl;
import pers.walyex.mybatis.proxy.MapperProxy;

import java.util.List;

public class MyDefaultSqlSession implements MySqlSession {

    private MyConfiguration myConfiguration;
    private MyExecutor myExecutor;

    public MyDefaultSqlSession(MyConfiguration myConfiguration) {
        this.myConfiguration = myConfiguration;
        this.myExecutor = new MySimpleExecutorImpl(myConfiguration);
    }

    @Override
    public <T> T selectOne(String statement, String parameter) {
        List<T> list = this.myExecutor.query(myConfiguration.getMyMapperStatementMap().get(statement), parameter);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statement, String parameter) {
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        //获取代理类，代理类包含MyDefaultSqlSession对象，则可以取到配置信息
        MapperProxy<T> mapperProxy = new MapperProxy<>(this, type);
        return mapperProxy.getProxy();
    }
}
