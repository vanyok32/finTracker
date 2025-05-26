package org.example.api;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebFilter("/*")
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e){
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ExceptionFilter init");
    }

}
