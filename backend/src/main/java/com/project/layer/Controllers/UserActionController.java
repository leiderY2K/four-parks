package com.project.layer.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Persistence.Entity.UserAction;
import com.project.layer.Services.Audit.AuditService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/userActions")
@RequiredArgsConstructor
public class UserActionController {

    private final AuditService auditService;

    @GetMapping("/allActions")
    public List<UserAction> getUserActionsList(){
        return auditService.getAllActions();
    }

    @GetMapping("{idUser}")
    public List<UserAction> getActionsByUser(@PathVariable("idUser") String idUser){
        return auditService.getActionsByUser(idUser);
    }
    
    



}
