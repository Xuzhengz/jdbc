package com.xzz.jdbc.practice;

import com.xzz.jdbc.day01.JDBCUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author 徐正洲
 * @date 2022/8/21-9:16
 * <p>
 * 操作顾客表，新增图片和下载图片
 */
public class CustomerJDBC {
    @Test
    public void test() throws Exception {
        Connection init = init();
        String sql = "insert into customers (cust_name,email,birth,photo) values(?,?,?,?)";
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("house.PNG");
        PreparedStatement preparedStatement = init.prepareStatement(sql);
        preparedStatement.setObject(1, "xuzz");
        preparedStatement.setObject(2, "2350122040@qq.com");
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        preparedStatement.setDate(3, date);
        preparedStatement.setBlob(4, resourceAsStream);
        preparedStatement.execute();
        init.close();
        preparedStatement.close();


    }


    public Connection init() throws Exception {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(systemResourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    @Test
    public void test2() throws Exception {
        Connection init = init();
//        String DDL = "CREATE TABLE if not exists `examstudent` (\n" +
//                "  `FlowID` int(10) NOT NULL AUTO_INCREMENT COMMENT '流水号',\n" +
//                "  `Type` int(5) DEFAULT NULL COMMENT '四级/六级',\n" +
//                "  `IDCard` varchar(255) DEFAULT NULL COMMENT '身份证号码',\n" +
//                "  `ExamCard` varchar(255) DEFAULT NULL COMMENT '准考证号码',\n" +
//                "  `StudentName` varchar(255) DEFAULT NULL COMMENT '学生姓名',\n" +
//                "  `Location` varchar(255) DEFAULT NULL COMMENT '区域',\n" +
//                "  `Grade` int(255) DEFAULT NULL COMMENT '成绩',\n" +
//                "  PRIMARY KEY (`FlowID`)\n" +
//                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入考试类型：");
        int Type = scanner.nextInt();
        System.out.print("请输入身份证号：");
        String IDCard = scanner.next();
        System.out.print("请输入准考证号：");
        String ExamCard = scanner.next();
        System.out.print("请输入考生姓名：");
        String StudentName = scanner.next();
        System.out.print("请输入区域：");
        String Location = scanner.next();
        System.out.print("请输入四六级成绩：");
        int Grade = scanner.nextInt();

        String sql = "insert into examstudent(`Type`,IDCard,ExamCard,StudentName,Location,Grade) " +
                "values(?,?,?,?,?,?)";
        PreparedStatement createTable = init.prepareStatement(sql);

        createTable.setInt(1, Type);
        createTable.setString(2, IDCard);
        createTable.setString(3, ExamCard);
        createTable.setString(4, StudentName);
        createTable.setString(5, Location);
        createTable.setInt(6, Grade);

        boolean result = createTable.execute();
        System.out.println(result);

//        if (createTable.execute() == true) {
//            System.out.println("添加成功");
//        } else {
//            System.out.println("添加失败");
//        }


        init.close();
        createTable.close();


    }

    /**
     * 考生信息查询功能
     */
    @Test
    public void test3() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择您要输入的类型：");
        System.out.println("a:准考证号");
        System.out.println("b:身份证号");
        char select = scanner.next().charAt(0);
        Connection init = init();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            if (select == 'a') {
                System.out.println("请输入准考证号：");
                String ExamCard = scanner.next();
                String sql = "select * from examstudent where ExamCard = ?";
                preparedStatement = init.prepareStatement(sql);
                preparedStatement.setString(1, ExamCard);
                resultSet = preparedStatement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columns = metaData.getColumnCount();
                if (resultSet.next() == true) {
                    while (resultSet.next()) {
                        for (int i = 0; i < columns; i++) {
                            System.out.println(metaData.getColumnLabel(i + 1) + "： " + resultSet.getObject(i + 1));
                        }
                    }
                } else {
                    System.out.println("查无信息！！！");
                }
            } else if (select == 'b') {
                System.out.println("请输入身份证号：");
                String IDCard = scanner.next();
                String sql = "select * from examstudent where IDCard = ?";
                preparedStatement = init.prepareStatement(sql);
                preparedStatement.setString(1, IDCard);
                resultSet = preparedStatement.executeQuery();
                int columns = resultSet.getMetaData().getColumnCount();
                ResultSetMetaData metaData = resultSet.getMetaData();
                if (resultSet.next() == true) {
                    while (resultSet.next()) {
                        for (int i = 0; i < columns; i++) {
                            System.out.println(metaData.getColumnLabel(i + 1) + "： " + resultSet.getObject(i + 1));
                        }
                    }
                } else {
                    System.out.println("查无信息！！！");
                    return;
                }

            } else {
                System.out.println("您的输入有误！请重新进入程序");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (init != null) {
                try {
                    init.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }

    /**
     * 删除考生信息
     */
    @Test
    public void test4() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入考生学号：");
            int flowId = scanner.nextInt();
            connection = init();
            String sql = "delete from examstudent where FlowID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, flowId);

            int i = preparedStatement.executeUpdate();
            if (i>0){
                System.out.println("删除成功");
            }else {
                System.out.println("查无此人！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    /**
     * 下载表中二进制图片信息
     */
    @Test
    public void test5(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.connection();
            String sql = "select * from customers where cust_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,4);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Blob photo = resultSet.getBlob("photo");
                //保存本地
                InputStream binaryStream = photo.getBinaryStream();
                FileOutputStream fileOutputStream = new FileOutputStream("xuzz.png");
                byte[] bytes = new byte[1024];
                int len;
                while ((len =binaryStream.read(bytes))!=-1){
                    fileOutputStream.write(bytes,0,len);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection,preparedStatement,resultSet);
        }
    }



}