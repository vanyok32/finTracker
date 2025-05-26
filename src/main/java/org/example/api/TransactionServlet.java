package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.TransactionRequestDTO;
import org.example.dto.TransactionResponseDTO;
import org.example.dto.UserRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.enums.TransactionCategory;
import org.example.enums.TransactionType;
import org.example.mappers.TransactionMapper;
import org.example.repositories.implementations.TransactionRepositoryImpl;
import org.example.service.implementations.TransactionServiceImpl;
import org.example.service.interfaces.TransactionService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/transactions/*")
public class TransactionServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final TransactionService transactionService = TransactionServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
            return;
        }
        String[] parts = pathInfo.split("/");
        if (parts.length == 2) {
            UUID id = UUID.fromString(parts[1]);
            TransactionResponseDTO trDTO = transactionService.getTransactionByID(id);
            if (trDTO != null) {
                objectMapper.writeValue(resp.getWriter(), trDTO);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found");
            }
        } else if (parts.length == 3 && parts[1].equals("user")) {
            UUID id = UUID.fromString(parts[2]);
            objectMapper.writeValue(resp.getWriter(), transactionService.getTransactionsByUserId(id));
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
            return;
        }
        transactionService.deleteTransaction(UUID.fromString(parts[1]));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            TransactionRequestDTO requestDTO = objectMapper.readValue(req.getReader(), TransactionRequestDTO.class);
            TransactionResponseDTO respDTO = transactionService.addTransaction(requestDTO, requestDTO.getUserId());
            objectMapper.writeValue(resp.getWriter(), respDTO);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid url");
            return;
        }
        TransactionRequestDTO requestDTO = objectMapper.readValue(req.getReader(), TransactionRequestDTO.class);
        TransactionResponseDTO respDTO = transactionService.updateTransaction(requestDTO, UUID.fromString(parts[1]));
        objectMapper.writeValue(resp.getWriter(), respDTO);
    }
    @Override
    public void init() throws ServletException {
        System.out.println("TransactionServlet init");
    }
    @Override
    public void destroy() {
    }
}