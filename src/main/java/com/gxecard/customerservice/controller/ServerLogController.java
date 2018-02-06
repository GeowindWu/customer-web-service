package com.gxecard.customerservice.controller;

import com.gxecard.customerservice.service.AccessControll;
import com.gxecard.customerservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/serviceLog")
public class ServerLogController {

    private MessageService messageService;
    private AccessControll accessControll;

    @Autowired
    public ServerLogController(MessageService messageService ,AccessControll accessControll) {
        this.messageService = messageService;
        this.accessControll = accessControll;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String serve(String logId, HttpServletRequest request) throws Exception {
    	System.out.println(accessControll);
        return messageService.queryLogList(logId);
    }
}
