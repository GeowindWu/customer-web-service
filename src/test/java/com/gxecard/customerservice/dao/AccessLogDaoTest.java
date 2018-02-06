package com.gxecard.customerservice.dao;

import com.gxecard.customerservice.entity.AccessLog;
import com.gxecard.customerservice.util.Utilitys;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AccessLogDaoTest {

    @Autowired
    private AccessLogDao dao;

    @Test
    public void updateTest(){
        AccessLog target = dao.findOne(1164);
        target.setSuccessTrage("1");
        target.setAccessUser("whq");
        target.setAccessResult("accessResultTest".getBytes());
        target.setServerInput("serverInputTest".getBytes());
        AccessLog save = dao.save(target);
        Assert.assertNotNull(save);

    }

    @Test
    public void findOneTest() throws SQLException {
        AccessLog target = dao.findOne(8546);
        byte[] output = target.getAccessResult();
        byte[] input = target.getServerInput();

        log.info("accessResult:{}",new String(output));
        log.info("accessInput：{}", new String(input) );
    }
    private String kfParam = "{\n" +
            "\t\"accessUser\": \"system_user\",\n" +
            "\t\"messageType\": \"10001\",\n" +
            "\t\"orderDesc\": \"wfcRecharege\",\n" +
            "\t\"orderTime\": \"20180129110101\",\n" +
            "\t\"outTradeNo\": \"0000000000001120\",\n" +
            "\t\"randomStr\": \"6673564482985892\",\n" +
            "\t\"totalFee\": \"1\",\n" +
            "\t\"userNo\": \"QA88882018012910001155\"\n" +
            "}";

    @Test
    public void saveTest(){
        AccessLog accessLog = new AccessLog();
        accessLog.setAccessUser("whq");
        accessLog.setAccessIp("192.1.1.142");
        accessLog.setApiCode("5002");
        accessLog.setSuccessTrage("1");
        accessLog.setAccessParams(kfParam.getBytes());

        // 第一步：生成访问Id
        String access_uuid;// 年月日时分秒+UUID
        String uuidStr = UUID.randomUUID().toString();
        access_uuid = Utilitys.getCurTimeByFromt("yyyyMMddHHmmss") + "_" + uuidStr;
        accessLog.setAccessUuid(access_uuid);
        Date current = new Date();
        accessLog.setAccessBegin(new Timestamp(current.getTime()));
        AccessLog save = dao.save(accessLog);
        log.info("插入数据：{}",save.toString());
        Assert.assertNotEquals((long)0,(long)save.getAccessid());
//        Assert.assertNotNull(save);



    }
}