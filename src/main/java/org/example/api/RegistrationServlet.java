package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.service.implementations.UserServiceImpl;
import org.example.service.interfaces.UserService;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final ObjectMapper objectMapper= new ObjectMapper();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        try{
            UserRequestDTO dto = objectMapper.readValue(req.getReader(), UserRequestDTO.class);
            if (userService.hasUser(dto.getEmail())) {
                objectMapper.writeValue(resp.getWriter(), userService.getUserByEmail(dto.getEmail()));
                return;
            }
            UserResponseDTO responseDTO = userService.registerUser(dto);
            objectMapper.writeValue(resp.getWriter(), responseDTO);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid name");
        }
    }
}
