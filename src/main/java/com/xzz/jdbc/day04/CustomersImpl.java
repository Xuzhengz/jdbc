package com.xzz.jdbc.day04;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author 徐正洲
 * @date 2022/8/23-19:45
 *
 * 实际操作表
 */
public class CustomersImpl extends BaseDao implements CustomersDao {
    @Override
    public void insert(Connection connection, JSONObject jsonObject) {
        String sql = "insert into customers(cust_name,email,birth,photo) values(?,?,?,?)";
        update(connection,sql,jsonObject.get("cust_name"),jsonObject.get("email"),jsonObject.get("birth"),jsonObject.get("photo"));
    }

    @Override
    public void delete(Connection connection, int id) {

    }

    @Override
    public void update(Connection connection, JSONObject jsonObject) {

    }

    @Override
    public void getJSONObjectById(Connection connection, int id) {

    }

    @Override
    public List<JSONObject> getAll(Connection connection) {
        return null;
    }

    @Override
    public Long getCount(Connection connection) {
        return null;
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        return null;
    }
}