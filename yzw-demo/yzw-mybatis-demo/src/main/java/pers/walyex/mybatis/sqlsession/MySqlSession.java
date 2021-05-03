package pers.walyex.mybatis.sqlsession;

import java.util.List;

/**
 * 定义数据库增删改查的方法
 * 所有的功能基于executor实现
 *
 * @author Waldron Ye
 * @date 2021/3/14 11:27
 */
public interface MySqlSession {

    /**
     * @param statement 对应sql语句
     * @param parameter 查询参数
     * @param <T>       返回指定的结果对象
     * @return
     */
    <T> T selectOne(String statement, String parameter);

    <T> List<T> selectList(String statement, String parameter);

    <T> T getMapper(Class<T> type);
}
