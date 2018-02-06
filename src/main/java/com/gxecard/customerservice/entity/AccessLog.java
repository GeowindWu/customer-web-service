package com.gxecard.customerservice.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@DynamicUpdate
@Data
public class AccessLog {
    @Id
    @GeneratedValue
    private Integer accessid;

    @Column(length = 120,nullable = false)
    private String accessUser;

    @Column(length = 60)
    private String apiName = "";

    @Column(length = 60)
    private String apiCode;

    /**
     * 客服端发起的唯一标识，主要用于客服端实时查询
     */
    @Column(length = 100)
    private String accessIndex = "";

    @Column(length = 100,nullable = false)
    private String accessUuid;

    @Column(length = 50)
    private String accessIp;

    @Column(nullable = false)
    private Timestamp accessBegin;

    @Column(length = 10)
    private  String  accessType = "0";//默认值0

    @Column
    private  byte[] accessParams;

    /**
     * 接口状态：0接口执行异常，1：接口执行正常
     */
    @Column(length = 6,nullable = false)
    private String successTrage = "0";

    /**
     * 访问状态。0：开始访问，1：访问结束。
     */
    @Column(length = 11,nullable = false)
    private Integer accessStatus = 0;

    @Column
    private Timestamp accessEnd;

    @Column
    private  byte[] accessResult;

    @Column(length = 10,nullable = false)
    private String accessBlobcharset = "GBK";

    @Column(length = 30)
    private String responseCode;

    @Column(length = 210)
    private String errorDesc;

    @Column
    private  byte[] serverInput;

    @Column
    private  byte[] serverReturn;

    @Column(length = 10)
    private String serverCharset = "GBK";

    @Column(length = 200)
    private String accessRemark;

    @Column(length = 11)
    private Integer analysis;
}
