package com.project.layer.Services.Access;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
@RequiredArgsConstructor

public class AccessService {
    @PostMapping(value="demo")
    public String welcome(){
      return "Welcome form secure endpoint";
    }

}
