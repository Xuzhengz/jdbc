package com.xzz.jdbc.day01;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 徐正洲
 * @date 2022/8/16-19:30
 */
public class ConnectTest {
    /**
     * 连接数据库最终版
     */

    public static void main(String[] args) throws Exception {
//        1、获取配置
        InputStream resourceAsStream = ConnectTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

//        2、加载配置
        Class.forName(driverClass);

//        3、获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);


    }
}