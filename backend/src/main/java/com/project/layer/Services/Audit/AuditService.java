package com.project.layer.Services.Audit;

import java.sql.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.UserAction;
import com.project.layer.Persistence.Repository.IUserActionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

    @Autowired
    private IUserActionRepository userActionRepository;

    public List<UserAction> getActionsByUser(String idUser) {
        List<UserAction> userActions = userActionRepository.getActionsByUser(idUser);

        if (userActions == null) {
            return null;
        }

        return userActions;

    }
    //Metodo que me filtra todas las acciones
    public List<UserAction> getAllActions() {
        List<UserAction> userActions = userActionRepository.getAllActions();
        return userActions;
    }

    // decoradores para sprintboot para que entienda que se esta modificando
    @Transactional
    @Modifying

    //metodo para agregar la acciòn realizada por el usuario
    public void setAction(UserAction userAction) {
        userActionRepository.save(userAction);
    }

    //Metodo que me devuleve las acciones filtradas
    public List<UserAction> getActionsFilter(String idUser, Date dateAction){
        List<UserAction> userActions = userActionRepository.getUserActionsByArgs(idUser, dateAction);
        return userActions;
    }

}
