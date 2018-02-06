package com.gxecard.customerservice.dao;

import com.gxecard.customerservice.constant.TradeStatus;
import com.gxecard.customerservice.entity.Charge;
import com.gxecard.customerservice.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ChargeDaoTest {
    @Autowired
    private ChargeDao dao;
    @Autowired
    private MessageService service;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void findOneTest() throws  Exception{
        Charge charge =  dao.findOne(1);
        log.info("查询结果：{}",charge.toString());
    }

    @Test
    public void saveTest() throws  Exception{
        Charge target = dao.findOne(2);
        log.info("查出数据：{}",target.toString());
        target.setStatus(TradeStatus.RECHARGED_AND_COMFIRM);
        Charge save = dao.save(target);
        log.info("更新数据后：{}",save.toString());
        assertNotNull(save);


    }

    @Test
    public void updateTest() throws  Exception{
        Charge charge = service.updateChargeStatusByOutTradeNoEquals("0000000000000994", 1);
        log.info("更新后：{}",charge);
        assertNotNull(charge);

    }
}