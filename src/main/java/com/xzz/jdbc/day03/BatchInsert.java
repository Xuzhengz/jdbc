package com.xzz.jdbc.day03;

import com.xzz.jdbc.day01.JDBCUtils;
import com.xzz.jdbc.practice.CustomerJDBC;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author 徐正洲
 * @date 2022/8/21-17:07
 */
public class BatchInsert {
    /**
     * 批量新增操作一：使用statement插入
     */
    @Test
    public void test() throws Exception {
        Connection connection = JDBCUtils.connection();
        Statement statement = connection.createStatement();
        for (int i = 0; i < 20000; i++) {
            String sql = "insert into goods(name) values('name_" + i + "')";
            statement.execute(sql);
        }
        connection.close();
        statement.close();
    }

    /**
     * 批量新增操作二：使用PreparedStatement插入
     */
    @Test
    public void test2() throws Exception {
        Connection connection = JDBCUtils.connection();
        String sql = "insert into goods(name) values(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < 20000; i++) {
            preparedStatement.setObject(1, "name" + i);
            preparedStatement.execute();
        }
        connection.close();

    }

    /**
     * 批量新增操作三：使用PreparedStatement插入，使用管道缓存
     */
    @Test
    public void test3(){
        Connection connection = null;
        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.connection();
//            connection.setAutoCommit(false);
            String sql = "insert into goods(name) values(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ArrayList<CustomerJDBC> customerJDBCS = new ArrayList<CustomerJDBC>();
            for (int i = 1; i <=1000000; i++) {
                preparedStatement.setObject(1, "name" + i);

                preparedStatement.addBatch();

                if (i % 500000 == 0){
                    preparedStatement.executeBatch();

                    preparedStatement.clearBatch();
                }
            }
//            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("共花费时长：" + ((end - start) / 1000) + "s");
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
}