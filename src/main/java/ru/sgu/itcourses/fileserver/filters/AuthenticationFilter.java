package ru.sgu.itcourses.fileserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgu.itcourses.fileserver.utils.UserBase;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Nikita Konovalov
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/login", "/", "/logout"})
public class AuthenticationFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);
    public static final String USER = "user";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            UserBase.loadUsers(filterConfig.getServletContext());
        } catch (Exception e) {
            LOG.error("Cant load users.txt", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        if (req.getRequestURI().equals("/logout")){
            session.removeAttribute(USER);
            resp.sendRedirect("/loginpage.html");
            return;
        }
        if (session.getAttribute(USER) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            if (login != null && password != null) {
                if (UserBase.authenticate(login, password)) {
                    session.setAttribute(AuthenticationFilter.USER, login);
                    resp.sendRedirect("/chat");
                } else {
                    resp.sendRedirect("/loginpage.html");
                }
            } else {
                resp.sendRedirect("/loginpage.html");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
