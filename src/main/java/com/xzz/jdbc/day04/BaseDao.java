package com.xzz.jdbc.day04;

import com.alibaba.fastjson.JSONObject;
import com.xzz.jdbc.day01.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.Set;

/**
 * @author 徐正洲
 * @date 2022/8/22-21:10
 * <p>
 * 封装了针对数据表的通用操作,抽象类
 */
public abstract class BaseDao<E> {
    private Class<E> aClass = null;

    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();// 获取父类的泛型
        aClass = (Class<E>) actualTypeArguments[0];
    }

    /**
     * 通用增删改操作
     */

    public int update(Connection connection, String sql, Object... objects) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            return preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(null, preparedStatement);
        }
        return 0;
    }

    /**
     * 通用的查询操作
     */
    @Test
    public static void searchData(Connection connection, String sql, Object... objects) throws Exception {
        connection = JDBCUtils.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i + 1, objects[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        while (resultSet.next()) {
//            System.out.println(metaData.getColumnCount());
            JSONObject jsObject = new JSONObject();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i + 1);
                String value = resultSet.getString(i + 1);
                jsObject.put(columnName, value);

                // 反射的方式赋予列属性，使用JSONObject无法使用
//                Field declaredField = SelectConnectTest.class.getDeclaredField(columnName);
//                declaredField.setAccessible(true);
//                declaredField.set(jsObject,value);
            }
            System.out.println(jsObject.toJSONString());
        }

        JDBCUtils.close(null, preparedStatement, resultSet);
    }

    /**
     * 查询特殊的方法
     */
    @Test
    public E getValue(Connection connection, String sql, Object... objects) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(null, preparedStatement, resultSet);
        }
        return null;
    }
}