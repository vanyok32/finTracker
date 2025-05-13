package org.example.entrypoint;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.UserRole;
@Setter
@Getter
public class UserRoleOwner {
    private static UserRoleOwner instance;
    private UserRole role;
    private UserRoleOwner(){}
    public static UserRoleOwner getInstance(){
        if(instance == null){
            instance = new UserRoleOwner();
        }
        return instance;
    }
}
