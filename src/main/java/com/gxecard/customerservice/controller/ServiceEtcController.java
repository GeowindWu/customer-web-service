package com.gxecard.customerservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceEtc")
public class ServiceEtcController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(String parameter) {

        return "error";
    }
}
