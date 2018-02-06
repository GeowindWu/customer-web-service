package com.gxecard.customerservice.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * 充值记录实体类
 */
@Entity
@Table(name = "charge")
@DynamicUpdate
@Data
public class Charge {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 16)
    private String code;

    @Column(length = 16)
    private String result;

    @Column(length = 64)
    private String msg;

    @Column(length = 1024)
    private String sign;

    @Column(length = 32)
    private String tradeNo;
    @Column(length = 32)
    private String outTradeNo;

    @Column(length = 64)
    private String userId;

    @Column(length = 16)
    private String totalFee;

    @Column(length = 20)
    private String payFee;

    @Column
    private Date payTime;

    @Column(nullable = false)
    private Integer status;

    public Charge() {
    }

    public Charge(String code, String result, String msg, String sign, String tradeNo, String outTradeNo, String userId, String totalFee, String payFee, Date payTime, Integer status) {
        this.code = code;
        this.result = result;
        this.msg = msg;
        this.sign = sign;
        this.tradeNo = tradeNo;
        this.outTradeNo = outTradeNo;
        this.userId = userId;
        this.totalFee = totalFee;
        this.payFee = payFee;
        this.payTime = payTime;
        this.status = status;
    }
}
