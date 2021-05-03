package pers.walyex.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import pers.walyex.mybatis.entity.SysUser;
import pers.walyex.mybatis.factory.MyDefaultSqlSessionFactory;
import pers.walyex.mybatis.factory.MySqlSessionFactory;
import pers.walyex.mybatis.mapper.SysUserMapper;
import pers.walyex.mybatis.sqlsession.MySqlSession;

import java.sql.*;

@Slf4j
public class MybatisDemoTest {

    public static final String URL = "jdbc:mysql://127.0.0.1:3306/user-manage?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    @Test
    public void queryTest() throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName(JDBC_DRIVER);
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sys_user");
        //如果有数据，rs.next()返回true
        while (rs.next()) {
            log.info(rs.getString("username") + " email：" + rs.getString("email"));
        }
        conn.close();
    }

    @Test
    public void test() {
        log.info("日志输出");

    }

    @Test
    public void mybatisTest() {

        MySqlSessionFactory sqlSessionFactory = new MyDefaultSqlSessionFactory();
        MySqlSession sqlSession = sqlSessionFactory.openSession();
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(2L);
        log.info("查询数据库:sysUser={}", sysUser);


    }
}
