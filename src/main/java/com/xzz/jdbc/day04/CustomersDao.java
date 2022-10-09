package com.xzz.jdbc.day04;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @author 徐正洲
 * @date 2022/8/23-19:41
 *
 * 针对表的接口规范
 */
public interface CustomersDao {
    void insert(Connection connection, JSONObject jsonObject);

    void delete(Connection connection, int id);

    void update(Connection connection, JSONObject jsonObject);

    void getJSONObjectById(Connection connection, int id);

    List<JSONObject> getAll(Connection connection);

    Long getCount(Connection connection);

    Date getMaxBirth(Connection connection);


}
