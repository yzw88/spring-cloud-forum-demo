package pers.walyex.mybatis.factory;

import pers.walyex.mybatis.sqlsession.MySqlSession;

public interface MySqlSessionFactory {
    MySqlSession openSession();

}
