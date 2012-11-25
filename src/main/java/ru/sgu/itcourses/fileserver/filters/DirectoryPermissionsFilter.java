package ru.sgu.itcourses.fileserver.filters;

import com.google.common.base.Joiner;
import ru.sgu.itcourses.fileserver.actions.DirectoryServlet;
import ru.sgu.itcourses.fileserver.utils.UserBase;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Konovalov
 */
@WebFilter(filterName = "DirectoryPermissionsFilter", urlPatterns = "/dir")
public class DirectoryPermissionsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getParameter(DirectoryServlet.PATH);
        String login = (String) req.getSession().getAttribute(AuthenticationFilter.USER);
        if (path == null) {
            resp.sendRedirect("/nopath.html");
            return;
        }
        if (path.equals("home")) {
            resp.sendRedirect("/dir?path=" + UserBase.getHomePathForUser(login));
            return;
        }
        path = path.toLowerCase();
        String normalPath = getNormalPath(path);
        String userRoot = UserBase.getHomePathForUser(login).toLowerCase();
        String userNormalRoot = getNormalPath(userRoot);
        if (!normalPath.startsWith(userNormalRoot)) {
            resp.sendRedirect("/denied.html");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private String getNormalPath(String path) {
        String[] splt = path.split("[/\\\\]");
        List<String> normalPath = new ArrayList<>();
        for (String s : splt) {
            if (s.equals(".")) {
                continue;
            }
            if (s.equals("..")) {
                normalPath.remove(normalPath.size() - 1);
                continue;
            }
            normalPath.add(s);
        }

        return Joiner.on("/").join(normalPath);

    }

    @Override
    public void destroy() {

    }
}
