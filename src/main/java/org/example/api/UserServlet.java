package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.Main;
import org.example.dto.UserResponseDTO;
import org.example.mappers.UserMapper;
import org.example.model.User;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.repositories.interfaces.UserRepository;
import org.example.service.imlementations.UserServiceImpl;
import org.example.service.interfaces.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserMapper userMapper = new UserMapper();
    private final UserService userService = new UserServiceImpl(UserRepositoryImpl.getInstance(),userMapper);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        System.out.println("UserServlet init");
    }

    @Override
    public void destroy() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println("PathInfo: " + pathInfo); // Выведет путь в консоль Tomcat
        resp.setContentType("application/json");
        try {
            String[] parts = pathInfo.split("/");
            if (parts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            UUID id = UUID.fromString(parts[1]);
            UserResponseDTO userDTO = userService.getUserById(id);
            if (userDTO != null) {
                objectMapper.writeValue(resp.getWriter(), userDTO);
            }
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }
    }
}
