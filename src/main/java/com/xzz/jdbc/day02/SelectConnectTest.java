package com.xzz.jdbc.day02;

import com.alibaba.fastjson.JSONObject;
import com.xzz.jdbc.day01.JDBCUtils;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author 徐正洲
 * @date 2022/8/17-20:26
 *
 *
 */
public class SelectConnectTest {
    public static void main(String[] args) throws Exception {
        SelectConnectTest selectConnectTest = new SelectConnectTest();
    }
    /**
     * 通用的查询操作
     */
    @Test
    public static void searchData(Connection connection, String sql,Object... objects) throws Exception{
        connection = JDBCUtils.connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            preparedStatement.setObject(i+1,objects[i]);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        while (resultSet.next()){
//            System.out.println(metaData.getColumnCount());
            JSONObject jsObject = new JSONObject();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i + 1);
                String value = resultSet.getString(i + 1);
                jsObject.put(columnName,value);

                // 反射的方式赋予列属性，使用JSONObject无法使用
//                Field declaredField = SelectConnectTest.class.getDeclaredField(columnName);
//                declaredField.setAccessible(true);
//                declaredField.set(jsObject,value);
            }
            System.out.println(jsObject.toJSONString());
        }

        JDBCUtils.close(null,preparedStatement,resultSet);


    }




    /**
     * 查询数据库操作
     */
    @Test
    public void findData() throws Exception{
        Connection connection = JDBCUtils.connection();
        String sql = "select quotaId,quotaName from test where quotaId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"wentao");
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        while (resultSet.next()){
            JSONObject jsonObject = new JSONObject();
            String columnName = metaData.getColumnName(1);
            String columnName2 = metaData.getColumnName(2);

            jsonObject.put(columnName,resultSet.getString(1));
            jsonObject.put(columnName2,resultSet.getString(2));

//            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                System.out.println(entry.getKey() + ":" + entry.getValue());
//            }

            System.out.println(jsonObject.toJSONString());

//            resultSet.
//            System.out.println(resultSet.);
        }
    }

}