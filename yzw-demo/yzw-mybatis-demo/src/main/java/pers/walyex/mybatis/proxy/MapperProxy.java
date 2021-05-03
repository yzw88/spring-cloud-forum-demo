package pers.walyex.mybatis.proxy;

import lombok.extern.slf4j.Slf4j;
import pers.walyex.mybatis.sqlsession.MySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * mapper代理类
 *
 * @author Waldron Ye
 * @date 2021/3/14 11:53
 */
@Slf4j
public class MapperProxy<T> implements InvocationHandler {

    private MySqlSession sqlSession;

    private Class<T> type;

    public MapperProxy(MySqlSession sqlSession, Class<T> type) {
        this.sqlSession = sqlSession;
        this.type = type;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        log.info("执行代理方法:method={}", method.getName());

        //todo 暂时写死一个方法
        return this.sqlSession.selectOne(method.getDeclaringClass().getName() + "," + method.getName(), objects == null ? null : objects[0].toString());
    }

    public T getProxy() {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, this);
    }
}
