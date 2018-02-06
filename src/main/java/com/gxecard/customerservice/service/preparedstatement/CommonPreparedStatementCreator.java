package com.gxecard.customerservice.service.preparedstatement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class CommonPreparedStatementCreator implements PreparedStatementCreator {

    // 通用属性名称，map对象必须包含的Map attrType:参数类型，attrVale:参数值
    private final static String[] containsKeys = {"attrType", "attrVale"};
    private final static String getAttrType(){
        return containsKeys[0];
    }
    private final static String getAttrValue(){
        return containsKeys[1];
    }

    public final static Map<String, Object> getProduceMap(int keyType, Object value){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CommonPreparedStatementCreator.getAttrType(), keyType);
        map.put(CommonPreparedStatementCreator.getAttrValue(), value);
        return map;
    }

    /**********************************************************************************/

    // 插入语句
    private String sqlStr;
    // 参数 Map attrType:参数类型，attrVale:参数值
    private List<Map<String, Object>> params;

    public CommonPreparedStatementCreator(String sqlStr, List<Map<String, Object>> params) {
        this.sqlStr = sqlStr;
        this.params = params;
        // 参数检查
        if (sqlStr == null || sqlStr.trim().length() == 0)
            throw new RuntimeException("参数不能为空");
        if (params == null)
            params = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < params.size(); ++i) {
            if (params.get(i) == null) {
                params.remove(i);// 无效数据移除
                --i;
            }
            if (params.get(i).containsKey(containsKeys[0]) == false) {// 类型是否正确
                params.remove(i);// 无效数据移除
                --i;
            }
            Integer.parseInt(params.get(i).get(containsKeys[0]).toString()); // 检验类类型是否正确
            if (params.get(i).containsKey(containsKeys[1]) == false) {// 数据
                params.remove(i);// 无效数据移除
                --i;
            }
        }
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sqlStr, PreparedStatement.RETURN_GENERATED_KEYS);
        // 构建查询语句
        for (int i = 0; i < params.size(); ++i) {
            Map<String, Object> map = params.get(i);
            int attrType = Integer.parseInt(map.get(containsKeys[0]).toString());
            switch (attrType) {
                case 10:
                    ps.setBytes(i + 1, (byte[]) map.get(containsKeys[1]));
                    break;
                default:
                    ps.setString(i + 1, map.get(containsKeys[1]).toString());
                    break;
            }
        }
        return ps;
    }
}