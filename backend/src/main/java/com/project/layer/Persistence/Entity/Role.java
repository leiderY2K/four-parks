package com.project.layer.Persistence.Entity;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    CLIENT(Arrays.asList(Permission.READ_ALL_PRODUCTS,Permission.CREATE_CLIENT)),
    ADMIN(Arrays.asList(Permission.READ_ALL_PRODUCTS,Permission.CREATE_CLIENT)),
    MANAGER(Arrays.asList(Permission.READ_ALL_PRODUCTS, Permission.SAVE_ONE_PRODUCT));

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    
}
