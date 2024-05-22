package com.project.layer.Services.Audit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.UserAction;
import com.project.layer.Persistence.Repository.IUserActionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

    @Autowired
    private IUserActionRepository userActionRepository;

    public List<UserAction> getActionsByUser(String idUser){
        List<UserAction> userActions = userActionRepository.getActionsByUser(idUser);

        if(userActions == null){
            return null;
        }

        return userActions;

    }

    public List<UserAction> getAllActions(){
        List<UserAction> userActions = userActionRepository.getAllActions();
        return userActions;
    }

}
