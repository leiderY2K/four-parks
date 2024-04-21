package com.project.layer.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/test")
public class PruebaController {
    
    @GetMapping
    public String getAll(){ return "Todo xd";}

    @PostMapping
    public String create(@RequestBody String test){ return test;}
}
