package pers.walyex.mybatis.executor;

import pers.walyex.mybatis.configuration.MyMapperStatement;

import java.util.List;

/**
 * mybatis核心接口，定义数据库操作的基本方法
 * jdbc，sqlSession都是基于它实现的
 * 该类的方法给代理类调用
 *
 * @author Waldron Ye
 * @date 2021/3/14 11:42
 */
public interface MyExecutor {

    /**
     * 查询
     * @param myMapperStatement 封装sql语句的myMapperStatement
     * @param parameter 传入sql参数
     * @param <T>
     * @return
     */
    <T> List<T> query(MyMapperStatement myMapperStatement, Object parameter);
}
