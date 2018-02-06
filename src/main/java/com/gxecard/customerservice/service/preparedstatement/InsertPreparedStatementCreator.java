package com.gxecard.customerservice.service.preparedstatement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertPreparedStatementCreator implements PreparedStatementCreator {

    // 插入语句
    private String sqlStr;
    // 参数
    private String[] params;

    /**
     * 针对每个参数都要对应值， null 设置为空
     * <p>
     * 每个参数都必须有输入，为空的参数写null 或者 ""
     *
     * @param sqlStr
     * @param params
     */
    public InsertPreparedStatementCreator(String sqlStr, String... params) {
        this.sqlStr = sqlStr;
        this.params = params;
        // 参数检查
        if (sqlStr == null || sqlStr.trim().length() == 0)
            throw new RuntimeException("参数不能为空");
        if (params == null)
            params = new String[0];

    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sqlStr, PreparedStatement.RETURN_GENERATED_KEYS);
        // 构建查询语句
        for (int i = 0; i < params.length; ++i) {
            if (params[i] != null)
                ps.setString(i + 1, params[i]);
            else
                ps.setString(i + 1, "");
        }
        return ps;
    }
}