package com.project.layer.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Persistence.Entity.UserAction;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Services.Audit.AuditService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Controller
@RequestMapping("/userActions")
@RequiredArgsConstructor

public class UserActionController {

    private final AuditService auditService;

    // Petición GET para trar el listado de las acciones
    @GetMapping("/allActions")
    public List<UserAction> getUserActionsList() {
        return auditService.getAllActions();
    }

    // Peticion Get para traer las acciones indicando el usuario
    @GetMapping("{idUser}")
    public List<UserAction> getActionsByUser(@PathVariable("idUser") String idUser) {
        return auditService.getActionsByUser(idUser);
    }

    @GetMapping("ActionsFilter")

    public List<UserAction> getActionsFilter(
            @RequestParam(required = false) String idUser,
            @RequestParam(required = false) Date dateAction,
            @RequestParam(required = false) String ipUser) {
        return auditService.getActionsFilter(idUser, dateAction, ipUser);
    }

    public AuditService getAuditService() {
        return auditService;
    }
}
