package com.gxecard.customerservice.controller;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import com.gxecard.customerservice.util.algorithm.des3.Des3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
    private MessageService messageService;
    private AccessControll accessControll;

    @Autowired
    public IndexController(MessageService messageService, AccessControll accessControll) {
        this.messageService = messageService;
        this.accessControll = accessControll;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(String parameter) {
        if (parameter == null)
            parameter = "未输入参数！";
        try {
            Des3Util.DeCoderDes3Func("29729797183748273429834298347238472938479283487238748347238748274328374823",33,34,"QJHSKDJJ==");
        }catch (Exception e){

        }        int accessId = messageService.insertDbLog("access_index", "-999999","", "127.0.0.1", "index:params Input");

        if (accessId > 0)
            messageService.updateServerParams(accessId, true, "index:params returns：测试汉字" + parameter, "-999999", "index:测试汉字", null, null, null);
        else
            messageService.updateServerParams(accessId, false, "index:params returns：测试汉字" + parameter, "-999999", "index:测试汉字", null, null, null);

        accessControll.initList(true);
        return "login";
    }
}
