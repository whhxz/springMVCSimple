package com.whh.controller;

import com.whh.controller.request.vo.DemoParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Demo
 * Created by xuzhuo on 2016/4/25.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/test")
    @ResponseBody
    public DemoParams test(@RequestBody DemoParams params){
        return params;
    }

    public String test2(@RequestParam("params")String params){return null;}
}

