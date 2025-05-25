package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.exceptions.ValidationException;
import org.example.mappers.UserMapper;
import org.example.model.User;
import org.example.repositories.implementations.UserRepositoryImpl;
import org.example.service.implementations.UserServiceImpl;
import org.example.service.interfaces.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        //System.out.println("PathInfo: " + pathInfo); // Выведет путь в консоль Tomcat
        resp.setContentType("application/json");
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                objectMapper.writeValue(resp.getWriter(), userService.getAllUsers());
                return;
            }
            String[] parts = pathInfo.split("/");
            if (parts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
                return;
            }
            UUID id = UUID.fromString(parts[1]);
            UserResponseDTO userDTO = userService.getUserById(id);
            if (userDTO != null) {
                objectMapper.writeValue(resp.getWriter(), userDTO);
            }
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid UUID");
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            String[] parts = pathInfo.split("/");
            if (parts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
                return;
            }
            UUID id = UUID.fromString(parts[1]);
            if (!userService.deleteUser(id)) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }

        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid UUID");
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json");
//        String pathInfo = req.getPathInfo();
//        System.out.println(pathInfo);
//        try{
//            if (/*pathInfo.equals("/create") || pathInfo.equals("/create/"*/
//                    pathInfo == null || pathInfo.equals("/")) {
//                UserRequestDTO dto = objectMapper.readValue(req.getReader(), UserRequestDTO.class);
//                if (userService.hasUser(dto.getEmail())) {
//                    objectMapper.writeValue(resp.getWriter(), userService.getUserByEmail(dto.getEmail()));
//                    return;
//                }
//                UserResponseDTO responseDTO = userService.registerUser(dto);
//                objectMapper.writeValue(resp.getWriter(), responseDTO);
//            }
//            else{
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
//            }
//        } catch (IllegalArgumentException e) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid name");
//        }
//    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        try{
            if (pathInfo == null || pathInfo.equals("/")) {
                UserRequestDTO dto = objectMapper.readValue(req.getReader(), UserRequestDTO.class);
                UUID id = UUID.fromString(req.getParameter("id"));
                UserResponseDTO respDTO = userService.updateUser(id, dto);
                objectMapper.writeValue(resp.getWriter(), respDTO);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
            }

        }catch (IllegalArgumentException | ValidationException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверные данные");
        }
    }
    @Override
    public void init() throws ServletException {
        System.out.println("INIT UserServlet");
    }

    @Override
    public void destroy() {

    }
}
