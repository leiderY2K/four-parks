package com.project.layer.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("support")
public class SupportController {

    @GetMapping
    public String hide(){
        return "hide";
    }

}
