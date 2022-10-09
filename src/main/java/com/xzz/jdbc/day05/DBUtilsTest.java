package com.xzz.jdbc.day05;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSONObject;
import com.xzz.jdbc.day01.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author 徐正洲
 * @date 2022/8/23-20:34
 * <p>
 * QueryRunner使用
 */
public class DBUtilsTest {
    Connection connection = null;

    @Test
    public void test() {
        try {
            QueryRunner queryRunner = new QueryRunner();

            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            Properties properties = new Properties();
            properties.load(resourceAsStream);

            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            connection = dataSource.getConnection();
            String sql = "update customers set cust_name =?";
            System.out.println(queryRunner.update(connection, sql, "3"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Test
    public void test2() throws Exception{
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = JDBCUtils.connection();
        String sql = "select * from customers";
//        BeanHandler<Customers> jsonObjectBeanHandler = new BeanHandler<Customers>(Customers.class);
//        BeanListHandler<Customers> customersBeanListHandler = new BeanListHandler<Customers>(Customers.class);
        MapListHandler mapListHandler = new MapListHandler();
        List<Map<String, Object>> query = queryRunner.query(connection, sql, mapListHandler);
        query.forEach(System.out::println);
        connection.close();
    }

}