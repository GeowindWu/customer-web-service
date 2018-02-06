package com.gxecard.customerservice.dao;

import com.gxecard.customerservice.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ChargeDao extends JpaRepository<Charge,Integer> {

    Charge findChargeByOutTradeNoEquals(String outTradeNo);
}
