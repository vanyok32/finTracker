package org.example.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.ValidationException;
import org.example.service.implementations.UserServiceImpl;
import org.example.service.interfaces.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private final UserService userService = UserServiceImpl.getInstance();
    private final ObjectMapper objectMapper= new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            String email;
            String pwd;
            Map<String, String> requestParams = objectMapper.readValue(req.getReader(), new TypeReference<Map<String, String>>() {
            });
            email = requestParams.get("email");
            pwd = requestParams.get("password");
            if (email == null || email.isEmpty() || pwd == null || pwd.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email и пароль обязательны");
                return;
            }
            boolean isAuthenticated = userService.loginUser(email, pwd);
            if (isAuthenticated) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"status\": \"Успешный вход\"}");
                String token = JWT.create()
                        .withSubject(email)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 час
                        .sign(Algorithm.HMAC256("secret_key"));
                resp.getWriter().write("{\"token\": \"" + token + "\"}");
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Неверные данные");
            }
        }catch (ValidationException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Введены неправильные данные");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка сервера");
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
