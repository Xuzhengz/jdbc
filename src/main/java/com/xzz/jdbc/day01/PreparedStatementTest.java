package com.xzz.jdbc.day01;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 徐正洲
 * @date 2022/8/16-20:20
 * <p>
 * 注：使用PreparedStatement可解决：
 * 1）SQL注入
 * 2）参数拼接
 */
public class PreparedStatementTest {
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;


    @Test
    public void test() throws Exception {
        PreparedStatementTest preparedStatementTest = new PreparedStatementTest();
        String sql = "insert into test values(?,?)";
        preparedStatementTest.sqlMethod(sql, "xuzhengzhou", "0816");
        JDBCUtils.close(connection,preparedStatement);
    }

    /**
     * 初始化
     */
    @Before
    public void init() throws Exception {
//        1、获取配置
        InputStream resourceAsStream = PreparedStatementTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
//        2、加载驱动
        Class.forName(driverClass);
//        3、获取连接
        connection = DriverManager.getConnection(url, user, password);

    }

    /**
     * 通用的操作
     */
    public void sqlMethod(String sql, Object... objects) throws Exception {
        preparedStatement = connection.prepareStatement(sql);

        //填充占位符
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        preparedStatement.execute();
    }

    /**
     * 增
     */
    @Test
    public void createData() throws SQLException {
        //  ??? 占位符
        String sql = "insert into test values(?,?)";
        preparedStatement = connection.prepareStatement(sql);

        //填充占位符数据
        preparedStatement.setString(1, "xzz");
        preparedStatement.setString(2, "male");

        //执行
        preparedStatement.execute();
        //关闭
        preparedStatement.close();
    }

    /**
     * 删
     */
    public void deleteData() {

    }

    /**
     * 改
     */
    @Test
    public void replaceData() throws Exception {
        String sql = "update test set quotaId = ? where quotaId = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, "wentao");
        preparedStatement.setString(2, "xzz");

        preparedStatement.execute();


    }

    /**
     * 查
     */
    public void selectData() {

    }

    @After
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}