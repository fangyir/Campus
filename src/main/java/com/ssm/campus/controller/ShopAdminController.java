package com.ssm.campus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopAdmin",method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping("/shopEdit")
    public String shopOperation() {
        return "shop//shopEdit";
    }
}
