package com.wonders.quartzlearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ExpanseWong
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String index(){
        return "index";
    }
}
