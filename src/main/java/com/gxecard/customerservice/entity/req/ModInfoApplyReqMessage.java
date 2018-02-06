package com.gxecard.customerservice.entity.req;

import com.gxecard.customerservice.annotation.SocketReqParam;
import com.gxecard.customerservice.entity.BaseReqMessage;

/**
 * 个人信息修改申请请求报文
 */
public class ModInfoApplyReqMessage extends BaseReqMessage {
    private String terminalNo = "";

    @SocketReqParam(order = 2, length = 16)
    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    private String terminalDeviceNo = "";

    @SocketReqParam(order = 3, length = 16)
    public String getTerminalDeviceNo() {
        return terminalDeviceNo;
    }

    public void setTerminalDeviceNo(String terminalDeviceNo) {
        this.terminalDeviceNo = terminalDeviceNo;
    }

    private String outletNo = "";

    @SocketReqParam(order = 4, length = 16)
    public String getOutletNo() {
        return outletNo;
    }

    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    private String cardNo = "";

    @SocketReqParam(order = 5, length = 19)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardPhysicsNo = "";

    @SocketReqParam(order = 6, length = 16)
    public String getCardPhysicsNo() {
        return cardPhysicsNo;
    }

    public void setCardPhysicsNo(String cardPhysicsNo) {
        this.cardPhysicsNo = cardPhysicsNo;
    }

    private String cardMasterType = "";

    @SocketReqParam(order = 7, length = 2)
    public String getCardMasterType() {
        return cardMasterType;
    }

    public void setCardMasterType(String cardMasterType) {
        this.cardMasterType = cardMasterType;
    }

    private String cardSubType = "";

    @SocketReqParam(order = 8, length = 4)
    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    private String cityCode = "";

    @SocketReqParam(order = 9, length = 4)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private String cardType = "";

    @SocketReqParam(order = 10, length = 2)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    private String staffFlag = "";

    @SocketReqParam(order = 11, length = 2)
    public String getStaffFlag() {
        return staffFlag;
    }

    public void setStaffFlag(String staffFlag) {
        this.staffFlag = staffFlag;
    }

    private String holderName = "";

    @SocketReqParam(order = 12, length = 40)
    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    private String holderIdNo = "";

    @SocketReqParam(order = 13, length = 36)
    public String getHolderIdNo() {
        return holderIdNo;
    }

    public void setHolderIdNo(String holderIdNo) {
        this.holderIdNo = holderIdNo;
    }

    private String holderIdType = "";

    @SocketReqParam(order = 14, length = 2)
    public String getHolderIdType() {
        return holderIdType;
    }

    public void setHolderIdType(String holderIdType) {
        this.holderIdType = holderIdType;
    }

    private String gender = "";

    @SocketReqParam(order = 15, length = 1)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String address = "";

    @SocketReqParam(order = 16, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String zipCode = "";

    @SocketReqParam(order = 17, length = 6)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    private String phone = "";

    @SocketReqParam(order = 18, length = 13)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String mobile = "";

    @SocketReqParam(order = 19, length = 12)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String fax = "";

    @SocketReqParam(order = 20, length = 13)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    private String email = "";

    @SocketReqParam(order = 21, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String randomNo = "";

    @SocketReqParam(order = 22, length = 8)
    public String getRandomNo() {
        return randomNo;
    }

    public void setRandomNo(String randomNo) {
        this.randomNo = randomNo;
    }

    private String operationType = "";

    @SocketReqParam(order = 23, length = 1)
    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    private String oldCardNo = "";

    @SocketReqParam(order = 24, length = 19)
    public String getOldCardNo() {
        return oldCardNo;
    }

    public void setOldCardNo(String oldCardNo) {
        this.oldCardNo = oldCardNo;
    }

    private String operatorId = "";

    @SocketReqParam(order = 25, length = 8)
    public String getOperatorId() {
        return operatorId;
    }

    private String proxyFlag = "";

    @SocketReqParam(order = 26, length = 1)
    public String getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(String proxyFlag) {
        this.proxyFlag = proxyFlag;
    }
}