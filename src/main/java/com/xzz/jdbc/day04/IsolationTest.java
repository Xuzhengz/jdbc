package com.xzz.jdbc.day04;

import com.xzz.jdbc.day01.JDBCUtils;
import com.xzz.jdbc.day02.SelectConnectTest;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author 徐正洲
 * @date 2022/8/22-20:42
 *
 * 隔离级别测试
 */
public class IsolationTest {
    @Test
    public void test1(){
        try {
            Connection connection = JDBCUtils.connection();
            int transactionIsolation = connection.getTransactionIsolation();
            System.out.println(transactionIsolation);
            connection.setAutoCommit(false);
            String sql="select * from goods where id =?";
            SelectConnectTest.searchData(connection,sql,"1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try {
            Connection connection = JDBCUtils.connection();
            connection.setAutoCommit(false);
            System.out.println(connection.getTransactionIsolation());
            String sql="update goods set name =? where id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,"4000");
            preparedStatement.setObject(2,"1");
            preparedStatement.execute();
            JDBCUtils.close(null,preparedStatement);

            Thread.sleep(15000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}