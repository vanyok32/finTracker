package org.example.entrypoint;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 *
 * Данный Singleton класс хранит id пользователя на протяжении всей сессии,
 * пока пользователь не выйдет из своей учетной записи
 *
 */

@Setter
@Getter
public class UserIdOwner {
    private static UserIdOwner instance;
    private UUID userID;

    private UserIdOwner(){}

    public static UserIdOwner getInstance(){
        if(instance == null){
            instance = new UserIdOwner();
        }
        return instance;
    }

}
