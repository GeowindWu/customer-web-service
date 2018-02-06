package com.gxecard.customerservice.service;

import com.gxecard.customerservice.entity.BaseRespErrorCommonMessage;
import com.gxecard.customerservice.entity.BaseRespMessage;
import com.gxecard.customerservice.util.Utilitys;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;

/**
 * Created by 郑川 on 2017/5/12.
 * <p>
 * 目的，延迟一秒钟，一步调用setDeffer方法
 * <p>
 * 达到返回结果的目的
 */
public class CallSetDefferThread extends Thread {
    private DeferredResult<BaseRespMessage> deferredResult;// 延迟返回的对象
    private String info;//需要延迟返回的内容

    private MessageService messageService;// 定义返回数据
    private int accessid;

    public CallSetDefferThread(int accessid, MessageService messageService, DeferredResult<BaseRespMessage> deferredResult, String info) {
        this.deferredResult = deferredResult;
        this.info = info == null ? "" : info;

        this.messageService = messageService;
        this.accessid = accessid;
    }

    @Override
    public void run() {
        // System.out.println(new Date().getTime());
        try {// 停止500毫米
            Thread.sleep(500);
        } catch (Exception e) {
        }
        // 如果失败则停止100度毫米
        for (long i = 0; i < 1000000; ++i) {
            long times = new Date().getTime();
            times *= Math.random();// 增加计算时间
            Long.class.equals(times);// 无用代码，防止警告
        }
        // 组件异常信息
        BaseRespErrorCommonMessage baseErrorResponse = new BaseRespErrorCommonMessage();
        baseErrorResponse.setResponseCode("-10000");
        baseErrorResponse.setErrorDescription(info);
        baseErrorResponse.setLogIndexId(this.accessid + "");

        // 记录日志
        try {
            if (accessid > 0)
                //messageService.updateDbLog(accessid,false, Utilitys.objectToMapByDepthOnce(baseErrorResponse).toString(),baseErrorResponse.getResponseCode(),baseErrorResponse.getErrorDescription());
                messageService.updateServerParams(accessid, false, Utilitys.objectToMapByDepthOnce(baseErrorResponse).toString(), baseErrorResponse.getResponseCode(), baseErrorResponse.getErrorDescription(), null, null, null);
        } catch (Exception e) {
        }
        // 返回前台信息
        deferredResult.setErrorResult(baseErrorResponse);
    }
}
