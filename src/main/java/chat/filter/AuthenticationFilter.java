package chat.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private Set<String> allowedUrls = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        allowedUrls.add("/login");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        if (userId == null && !allowedUrls.contains(req.getServletPath())) {
            resp.sendRedirect("/login");
            return;
        }
        chain.doFilter(req, resp);
    }
}
