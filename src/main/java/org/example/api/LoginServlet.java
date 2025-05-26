package org.example.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.ValidationException;
import org.example.service.implementations.LoginServiceImpl;
import org.example.service.implementations.UserServiceImpl;
import org.example.service.interfaces.LoginService;
import org.example.service.interfaces.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private final UserService userService = UserServiceImpl.getInstance();
    private final ObjectMapper objectMapper= new ObjectMapper();
    private final LoginService loginService = LoginServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Map<String, String> requestParams = objectMapper.readValue(req.getReader(), new TypeReference<Map<String, String>>() {
        });
        String email = requestParams.get("email");
        String pwd = requestParams.get("password");
        if(userService.loginUser(email, pwd)){
            String jwt = loginService.getJWT(email);
            resp.getWriter().write("{\"token\": \"" + jwt + "\"}");
        }
        else{
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid email or password");
        }

    }

    @Override
    public void init() throws ServletException {
        System.out.println("LoginServlet init");
    }

    @Override
    public void destroy() {

    }
}
