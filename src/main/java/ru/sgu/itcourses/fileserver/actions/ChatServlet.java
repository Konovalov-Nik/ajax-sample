package ru.sgu.itcourses.fileserver.actions;

import ru.sgu.itcourses.fileserver.filters.AuthenticationFilter;
import ru.sgu.itcourses.fileserver.utils.Message;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nikita Konovalov
 */
@WebServlet(urlPatterns = {"/chat", "/messages"})
public class ChatServlet extends HttpServlet {
    private static List<Message> messages;

    @Override
    public void init(ServletConfig config) throws ServletException {
        messages = Collections.synchronizedList(new ArrayList<Message>());
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getRequestURI()) {
            case "/chat": {
                doGetChatPage(req, resp);
                return;
            }
            case "/messages": {
                doGetMessages(req, resp);
                return;
            }
        }
    }

    private void doGetChatPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("user");
        req.setAttribute("username", username);

        req.getRequestDispatcher("/chat.jsp").forward(req, resp);
    }

    private void doGetMessages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("messages", messages);

        req.getRequestDispatcher("/messages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        String username = (String) req.getSession().getAttribute("user");

        messages.add(new Message(username, text));
    }
}
