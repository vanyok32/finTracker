package org.example.service.implementations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.service.interfaces.LoginService;

import java.util.Date;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class    LoginServiceImpl implements LoginService {
    private final static LoginServiceImpl INSTANCE = new LoginServiceImpl();
    @Override
    public String getJWT(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 час
                .sign(Algorithm.HMAC256("secret_key"));
    }

    public static LoginServiceImpl getInstance() {
        return INSTANCE;
    }
}
