package uk.co.aaronburt.ipmi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UIController {

    @GetMapping("/")
    public String index(){
        return "Index";
    }

    @GetMapping("/error")
    public String catchAll(){
        return "Error";
    }
}
