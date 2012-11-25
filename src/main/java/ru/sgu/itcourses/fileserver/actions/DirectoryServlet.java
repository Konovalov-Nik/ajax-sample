package ru.sgu.itcourses.fileserver.actions;

import ru.sgu.itcourses.fileserver.filters.AuthenticationFilter;
import ru.sgu.itcourses.fileserver.utils.HtmlUtil;
import ru.sgu.itcourses.fileserver.utils.UserBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Nikita Konovalov
 */
@WebServlet(urlPatterns = {"/dir"})
public class DirectoryServlet extends HttpServlet {

    public static final String PATH = "path";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter(PATH);
        String login = (String) req.getSession().getAttribute(AuthenticationFilter.USER);

        PrintWriter out = resp.getWriter();
        String htmlByPath = HtmlUtil.getHtmlByPath(new File(path), login);
        if (htmlByPath == null) {
            resp.sendRedirect("/wrongpath.html");
            return;
        }
        out.write(htmlByPath);
    }
}
