package com.gxecard.customerservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class CustomErrorController extends BasicErrorController {
    @Autowired
    public CustomErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);

        //输出自定义的Json格式
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("responseCode","90000");
        try {
            map.put("responseCode", "90" + status.value());
            String errorStr = "error:";
            errorStr = errorStr + (body.containsKey("error") ? body.get("error") : new Object());
            errorStr = errorStr + ". message:";
            errorStr = errorStr + (body.containsKey("message") ? body.get("message") : new Object());
            map.put("errorDescription", errorStr);
        } catch (Exception e) {
        }
        log.error("asynchronous get data exception ！", "CustomErrorController::error:" + map.toString());
        return new ResponseEntity<>(map, status);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
                request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());

        return new ModelAndView("error", model);
    }

}
