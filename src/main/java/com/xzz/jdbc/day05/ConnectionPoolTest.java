package com.xzz.jdbc.day05;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author 徐正洲
 * @date 2022/8/23-20:04
 *
 * Druid数据库连接池
 */
public class ConnectionPoolTest {

    @Test
    public void getConnect() throws Exception {
        Properties properties = new Properties();
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        String sql = "select * from customers";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()){
            for (int i = 0; i < columnCount; i++) {
                System.out.print(resultSet.getObject(i + 1));
            }
        }

        connection.close();
        preparedStatement.close();
        resultSet.close();
    }
}