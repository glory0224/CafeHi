package com.cafeHi.www.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommonController {

    @GetMapping("/Preparing")
    public String PreparingMessage(HttpServletRequest request) {
        request.setAttribute("msg", "준비중입니다 :)");
        return "common/goBackAlert";
    }
}
