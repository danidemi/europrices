package com.danidemi.europrice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value="/okko", method=RequestMethod.GET)
    public String root(Model model) {
        return "index";
    }
	
}
