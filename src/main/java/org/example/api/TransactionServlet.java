package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserResponseDTO;
import org.example.model.User;
import org.example.service.interfaces.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("users/*")
@RequiredArgsConstructor
public class TransactionServlet extends HttpServlet {
}